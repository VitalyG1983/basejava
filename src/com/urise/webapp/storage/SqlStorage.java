package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.*;
import com.urise.webapp.sql.ConnectionFactory;
import com.urise.webapp.sql.SqlHelper;
import com.urise.webapp.util.JsonParser;

import java.sql.*;
import java.util.*;

public class SqlStorage implements Storage {
    public final ConnectionFactory connectionFactory;
    public final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        sqlHelper = new SqlHelper(connectionFactory);
    }

    @Override
    public void clear() {
        sqlHelper.doCommonCode("DELETE FROM resume", PreparedStatement::execute);
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.doCommonCode("SELECT r.uuid AS rUuid, full_name, c.type AS cType, c.value AS cValue, " +
                "s.type AS sType, s.value AS sValue FROM resume r LEFT JOIN contact c ON r.uuid = c.resume_uuid " +
                "LEFT JOIN section s ON uuid = s.resume_uuid  WHERE r.uuid =? ", ps -> {
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
            doDelete("DELETE FROM contact c WHERE c.resume_uuid=?", r.getUuid(), conn);
            doDelete("DELETE FROM section s WHERE s.resume_uuid=?", r.getUuid(), conn);
            doSqlResume("UPDATE resume SET full_name = ? WHERE uuid = ?", conn, r);
            insertSqlContacts(conn, r);
            insertSqlSections(conn, r);
            return null;
        });
    }

    @Override
    public void save(Resume r) {
        sqlHelper.transactionalExecute(conn -> {
            doSqlResume("INSERT INTO resume (full_name, uuid) VALUES (?,?)", conn, r);
            insertSqlContacts(conn, r);
            insertSqlSections(conn, r);
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
        return sqlHelper.transactionalExecute(conn -> {
            Map<String, Resume> resumes;
            try (PreparedStatement psResume = conn.prepareStatement("SELECT * FROM resume ORDER BY full_name, uuid")) {
                ResultSet rsResume = psResume.executeQuery();
                Resume r = new Resume();
                resumes = new LinkedHashMap<>();
                while (rsResume.next()) {
                    String uuid = rsResume.getString("uuid").trim();
                    if (!resumes.containsKey(uuid)) {
                        r = new Resume(uuid, rsResume.getString("full_name"));
                        resumes.put(uuid, r);
                    }
                }
            }
            try (PreparedStatement psContact = conn.prepareStatement("SELECT  c.resume_uuid AS cResume_uuid, " +
                    "c.type AS cType, c.value AS cValue FROM contact c")) {
                ResultSet rsContact = psContact.executeQuery();
                while (rsContact.next()) {
                    String resume_uuid = rsContact.getString("cResume_uuid").trim();
                    if (resumes.containsKey(resume_uuid)) {
                        fillContacts(rsContact, resumes.get(resume_uuid));
                    }
                }
            }
            try (PreparedStatement psSection = conn.prepareStatement("SELECT s.resume_uuid AS sResume_uuid, " +
                    "s.type AS sType, s.value AS sValue  FROM section s")) {
                ResultSet rsSection = psSection.executeQuery();
                while (rsSection.next()) {
                    String section_uuid = rsSection.getString("sResume_uuid").trim();
                    if (resumes.containsKey(section_uuid)) {
                        fillSections(rsSection, resumes.get(section_uuid));
                    }
                }
            }
            return new ArrayList<>(resumes.values());
        });
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

    private void insertSqlContacts(Connection conn, Resume r) throws SQLException {
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

    private void insertSqlSections(Connection conn, Resume r) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO section (value, resume_uuid, type) " +
                "VALUES (?,?,?)")) {
            Map<SectionType, AbstractSection> sections = r.getSections();
            for (EnumMap.Entry<SectionType, AbstractSection> entry : sections.entrySet()) {
                SectionType sectionType = entry.getKey();
                AbstractSection entryValue = entry.getValue();
              /*  switch (sectionType) {
                    case PERSONAL, OBJECTIVE -> {
                        String text = ((TextSection) entryValue).getText();
                        if (text != null) {
                            ps.setString(1, text);
                        }
                    }
                    case ACHIEVEMENT, QUALIFICATIONS -> {
                        List<String> listSection = ((TextListSection) entryValue).getListSection();
                        if (listSection != null) {
                            String joinedList = String.join("\n", listSection);
                            ps.setString(1, joinedList);
                        }
                    }
                }*/
                ps.setString(1, JsonParser.write(entryValue, AbstractSection.class));
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
        String typeContact = rs.getString("cType");
        if (typeContact != null) {
            boolean isContact = r.getContacts().containsKey(ContactType.valueOf(typeContact));
            if (!isContact) {
                r.addContact(ContactType.valueOf(typeContact), rs.getString("cValue"));
            }
        }
    }

    private void fillSections(ResultSet rs, Resume r) throws SQLException {
        String typeSection = rs.getString("sType");
        if (typeSection != null) {
            boolean isSection = r.getSections().containsKey(SectionType.valueOf(typeSection));
            if (!isSection) {
                SectionType sectionType = SectionType.valueOf(typeSection);
                String sectionText = rs.getString("sValue");
                r.addSection(sectionType, JsonParser.read(sectionText, AbstractSection.class));
               /* Map<SectionType, AbstractSection> sections = r.getSections();
                switch (sectionType) {
                    case PERSONAL, OBJECTIVE -> sections.put(sectionType.equals(SectionType.PERSONAL) ? SectionType.PERSONAL :
                            SectionType.OBJECTIVE, new TextSection(sectionText));
                    case ACHIEVEMENT, QUALIFICATIONS -> fillTextListSection(sectionText,
                            sections, sectionType.equals(SectionType.ACHIEVEMENT) ? SectionType.ACHIEVEMENT : SectionType.QUALIFICATIONS);
                }*/
            }
        }
    }

    private void fillTextListSection(String sectionText, Map<SectionType, AbstractSection> sections, SectionType
            secType) {
        TextListSection tls = new TextListSection(new ArrayList<>());
        List<String> listString = tls.getListSection();
        if (sectionText != null) {
            String[] stringArray = sectionText.split("\n");
            listString.addAll(Arrays.asList(stringArray));
        }
        sections.put(secType, tls);
    }
}