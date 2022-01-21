package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.ContactType;
import com.urise.webapp.model.Resume;
import com.urise.webapp.sql.ConnectionFactory;
import com.urise.webapp.sql.ExceptionUtil;
import com.urise.webapp.sql.SqlHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SqlStorage implements Storage {
    public final ConnectionFactory connectionFactory;
    public final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        sqlHelper = new SqlHelper(connectionFactory);
    }

    @Override
    public void clear() {
        sqlHelper.doCommonCode("DELETE FR OM resume; DELETE FROM contact", PreparedStatement::execute);
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.doCommonCode("" + "    SELECT * FROM resume r " + " LEFT JOIN contact c " + "        ON r.uuid = c.resume_uuid " + "     WHERE r.uuid =? ", ps -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            checkForException(!rs.next(), uuid);

            Resume r = new Resume(uuid, rs.getString("full_name"));
            do {
                String value = rs.getString("value");
                ContactType type = ContactType.valueOf(rs.getString("type"));
                r.addContact(type, value);
            } while (rs.next());
            return r;
        });
    }

    @Override
    public void update(Resume r) {
        sqlHelper.transactionalExecute(conn -> {
            doSqlResume("UPDATE resumes.public.resume SET full_name = ? WHERE uuid = ?", conn, r);
            return doSqlContact("UPDATE resumes.public.contact " + "SET  value = ? " + "WHERE resume_uuid = ? AND type = ?", conn, r);
        });
    }

    @Override
    public void save(Resume r) {
        sqlHelper.transactionalExecute(conn -> {
            doSqlResume("INSERT INTO resumes.public.resume (full_name, uuid) VALUES (?,?)", conn, r);
            return doSqlContact("INSERT INTO resumes.public.contact (value,resume_uuid,type) VALUES (?,?,?)", conn, r);
        });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.doCommonCode("DELETE FROM resume WHERE uuid=?", ps -> {
            ps.setString(1, uuid);
            checkForException(ps.executeUpdate() != 1, uuid);
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.doCommonCode("" + "    SELECT * FROM resume r " + " LEFT JOIN contact c " + "        ON r.uuid = c.resume_uuid ", ps -> {
            List<Resume> resumes = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            String uuid;
            String uuidOld = null;
            Resume r = new Resume();
            while (rs.next()) {
                uuid = rs.getString("uuid");
                if (!uuid.equals(uuidOld)) {
                    r = new Resume(uuid, rs.getString("full_name"));
                    resumes.add(r);
                }
                r.addContact(ContactType.valueOf(rs.getString("type")), rs.getString("value"));
                uuidOld = uuid;
            }
            return resumes;
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

    private void doSqlResume(String sql, Connection conn, Resume r) {
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            String uuid = r.getUuid();
            ps.setString(1, r.getFullName());
            ps.setString(2, uuid);
            checkForException(ps.executeUpdate() != 1, uuid);
        } catch (SQLException e) {
            throw ExceptionUtil.convertException(e);
        }
    }

    Object doSqlContact(String sql, Connection conn, Resume r) {
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            for (Map.Entry<ContactType, String> e : r.getContacts().entrySet()) {
                ps.setString(1, e.getValue());
                ps.setString(2, r.getUuid());
                ps.setString(3, e.getKey().name());
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e) {
            throw ExceptionUtil.convertException(e);
        }
        return null;
    }
}