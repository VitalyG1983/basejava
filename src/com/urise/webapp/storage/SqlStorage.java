package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.*;
import com.urise.webapp.sql.ConnectionFactory;
import com.urise.webapp.sql.SqlHelper;

import java.sql.*;
import java.util.*;

public class SqlStorage implements Storage {
    public final ConnectionFactory connectionFactory;
    public final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        sqlHelper = new SqlHelper(connectionFactory);
    }

    @Override
    public void clear() {
        sqlHelper.doCommonCode("DELETE FROM resume", PreparedStatement::execute);
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.doCommonCode("SELECT * FROM resume LEFT JOIN contact ON uuid = resume_uuid " +
                "LEFT JOIN section ON uuid = section_uuid  WHERE uuid =? ", ps -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            checkForException(!rs.next(), uuid);
            Resume r = new Resume(uuid, rs.getString("full_name"));
            do {
                fillContacts(rs, r);
                fillSections(rs, r);
            }
            while (rs.next());
            return r;
        });
    }

    @Override
    public void update(Resume r) {
        sqlHelper.transactionalExecute(conn -> {
            doDelete("DELETE FROM contact WHERE resume_uuid=?", r.getUuid(), conn);
            doDelete("DELETE FROM section WHERE section_uuid=?", r.getUuid(), conn);
            doSqlResume("UPDATE resume SET full_name = ? WHERE uuid = ?", conn, r);
            insertSqlContact(conn, r);
            insertSqlSection(conn, r);
            return null;
        });
    }

    @Override
    public void save(Resume r) {
        sqlHelper.transactionalExecute(conn -> {
            doSqlResume("INSERT INTO resume (full_name, uuid) VALUES (?,?)", conn, r);
            insertSqlContact(conn, r);
            insertSqlSection(conn, r);
            return null;
        });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.doCommonCode("DELETE FROM resume WHERE uuid=?", ps -> {
            ps.setString(1, uuid);
            checkForException(ps.executeUpdate() == 0, uuid);
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> list = new ArrayList<>();
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement psResume = conn.prepareStatement("SELECT * FROM resume ORDER BY full_name, uuid");
                 PreparedStatement psContact = conn.prepareStatement("SELECT * FROM contact");
                 PreparedStatement psSection = conn.prepareStatement("SELECT * FROM section")) {
                ResultSet rsResume = psResume.executeQuery();
                Resume r = new Resume();
                Map<String, Resume> resumes = new LinkedHashMap<>();
                while (rsResume.next()) {
                    String uuid = rsResume.getString("uuid").trim();
                    if (!resumes.containsKey(uuid)) {
                        r = new Resume(uuid, rsResume.getString("full_name"));
                        resumes.put(uuid, r);
                    }
                }
                ResultSet rsContact = psContact.executeQuery();
                while (rsContact.next()) {
                    String resume_uuid = rsContact.getString("resume_uuid").trim();
                    if (resumes.containsKey(resume_uuid)) {
                        fillContacts(rsContact, resumes.get(resume_uuid));
                    }
                }
                ResultSet rsSection = psSection.executeQuery();
                while (rsSection.next()) {
                    String section_uuid = rsSection.getString("section_uuid").trim();
                    if (resumes.containsKey(section_uuid)) {
                        fillSections(rsSection, resumes.get(section_uuid));
                    }
                }
                list.addAll(resumes.values());
            }
            return null;
        });
        return list;
   /*     return sqlHelper.doCommonCode("SELECT * FROM resume r LEFT JOIN contact c ON r.uuid = c.resume_uuid " +
                        "ORDER BY full_name, uuid",
                ps -> {
                    Resume r = new Resume();
                    Map<String, Resume> resumes = new LinkedHashMap<>();
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        String uuid = rs.getString("uuid");
                        if (!resumes.containsKey(uuid)) {
                            r = new Resume(uuid, rs.getString("full_name"));
                            resumes.put(uuid, r);
                        }
                        fillResume(rs, r);
                    }
                    return new ArrayList<>(resumes.values());
                });*/
    }

    @Override
    public int size() {
        return sqlHelper.doCommonCode("SELECT COUNT(*) FROM resume", ps -> {
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt("count") : 0;
        });
    }

    private void checkForException(Boolean notExist, String uuid) {
        if (notExist) {
            throw new NotExistStorageException(uuid);
        }
    }

    private void doSqlResume(String sql, Connection conn, Resume r) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            String uuid = r.getUuid();
            ps.setString(1, r.getFullName());
            ps.setString(2, uuid);
            checkForException(ps.executeUpdate() != 1, uuid);
        }
    }

    private void insertSqlContact(Connection conn, Resume r) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact (value,resume_uuid,type) VALUES (?,?,?)")) {
            for (Map.Entry<ContactType, String> e : r.getContacts().entrySet()) {
                ps.setString(1, e.getValue());
                ps.setString(2, r.getUuid());
                ps.setString(3, e.getKey().name());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void insertSqlSection(Connection conn, Resume r) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO section (text, section_uuid, section_type) " +
                "VALUES (?,?,?)")) {
            Map<SectionType, AbstractSection> sections = r.getSections();
            for (EnumMap.Entry<SectionType, AbstractSection> entry : sections.entrySet()) {
                TextSection textSection = entry.getValue() instanceof TextSection ? ((TextSection) entry.getValue()) : null;
                TextListSection textListSection = entry.getValue() instanceof TextListSection ?
                        ((TextListSection) entry.getValue()) : null;
                if (textSection != null) {
                    ps.setString(1, textSection.getText());
                } else if (textListSection != null) {
                    String text = "";
                   /* for (String oneLine : textListSection.getListSection()) {
                        text = text + oneLine + "\n";
                    }*/
                    text = textListSection.getListSection().stream().reduce(text, (result, x) -> result + x + "\n");
                    ps.setString(1, text);
                }
                ps.setString(2, r.getUuid());
                ps.setString(3, entry.getKey().name());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void doDelete(String sql, String uuid, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, uuid);
            ps.execute();
        }
    }

    private void fillContacts(ResultSet rs, Resume r) throws SQLException {
        String typeContact = rs.getString("type");
        if (typeContact != null) {
            boolean isContact = r.getContacts().containsKey(ContactType.valueOf(typeContact));
            if (!isContact) {
                r.addContact(ContactType.valueOf(typeContact), rs.getString("value"));
            }
        }
    }

    private void fillSections(ResultSet rs, Resume r) throws SQLException {
        String type_section = rs.getString("section_type");
        if (type_section != null) {
            boolean isSection = r.getSections().containsKey(SectionType.valueOf(type_section));
            if (!isSection) {
                SectionType sectionType = SectionType.valueOf(type_section);
                String sectionText = rs.getString("text");
                Map<SectionType, AbstractSection> sections = r.getSections();
                switch (sectionType) {
                    case PERSONAL, OBJECTIVE -> sections.put(sectionType.equals(SectionType.PERSONAL) ? SectionType.PERSONAL :
                            SectionType.OBJECTIVE, new TextSection(sectionText));
                    case ACHIEVEMENT, QUALIFICATIONS -> fillTextListSection(sectionText,
                            sections, sectionType.equals(SectionType.ACHIEVEMENT) ? SectionType.ACHIEVEMENT : SectionType.QUALIFICATIONS);
                }
            }
        }
    }

    private void fillTextListSection(String sectionText, Map<SectionType, AbstractSection> sections, SectionType secType) {
        TextListSection tls = new TextListSection(new ArrayList<>());
        List<String> listString = tls.getListSection();
        if (sectionText != null) {
            String[] stringArray = sectionText.split("\n");
            listString.addAll(Arrays.asList(stringArray));
        }
        sections.put(secType, tls);
    }
}