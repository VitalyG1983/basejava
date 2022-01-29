package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.ContactType;
import com.urise.webapp.model.Resume;
import com.urise.webapp.sql.ConnectionFactory;
import com.urise.webapp.sql.ExceptionUtil;
import com.urise.webapp.sql.SqlHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.urise.webapp.storage.AbstractStorage.RESUME_NAME_COMPARATOR;

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
        return sqlHelper.doCommonCode("SELECT * FROM resume r LEFT JOIN contact c ON r.uuid = c.resume_uuid " +
                "WHERE r.uuid =? ", ps -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            // boolean next = rs.next();
            checkForException(!rs.next(), uuid);
            Resume r = new Resume(uuid, rs.getString("full_name"));
            // while (next) {
            do
                addContact(rs, r);
            while (rs.next());
            //   next = rs.next();
            //  }
            return r;
        });
    }

    @Override
    public void update(Resume r) {
        sqlHelper.transactionalExecute(conn -> {
            boolean isContacts = false;
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM contact WHERE resume_uuid =? ")) {
                ps.setString(1, r.getUuid());
                if (ps.execute())
                    isContacts = true;
            }
            if (isContacts)
                doDelete("DELETE FROM contact c WHERE c.resume_uuid = ?", r.getUuid(), conn);
            doSqlResume("UPDATE resume SET full_name = ? WHERE uuid = ?", conn, r);
            doSqlContact(conn, r);
            return null;
        });
    }

    @Override
    public void save(Resume r) {
        sqlHelper.transactionalExecute(conn -> {
            doSqlResume("INSERT INTO resume (full_name, uuid) VALUES (?,?)", conn, r);
            doSqlContact(conn, r);
            return null;
        });
    }

    @Override
    public void delete(String uuid) {
        try {
            Connection conn = connectionFactory.getConnection();
            doDelete("DELETE FROM resume WHERE uuid=?", uuid, conn);
        } catch (SQLException e) {
            ExceptionUtil.convertException(e);
        }
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.doCommonCode("SELECT * FROM resume r LEFT JOIN contact c ON r.uuid = c.resume_uuid " +
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
                        addContact(rs, r);
                    }
                    List<Resume> list = new ArrayList<>(resumes.values());
                    list.sort(RESUME_NAME_COMPARATOR);
                    return list;
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

    private void doSqlContact(Connection conn, Resume r) throws SQLException {
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

    private void doDelete(String sql, String uuid, Connection conn) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, uuid);
        checkForException(ps.executeUpdate() == 0, uuid);

      /*  sqlHelper.doCommonCode(sql, ps -> {
            ps.setString(1, uuid);
            checkForException(ps.executeUpdate() == 0, uuid);
            return null;
        });*/
    }

    private void addContact(ResultSet rs, Resume r) throws SQLException {
        // String value = rs.getString("value");
        // if (!StringUtils.isBlank(value))
        r.addContact(ContactType.valueOf(rs.getString("type")), rs.getString("value"));
    }
}