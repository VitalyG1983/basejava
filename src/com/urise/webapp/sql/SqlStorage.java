package com.urise.webapp.sql;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.Storage;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

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
        return sqlHelper.doCommonCode("SELECT * FROM resume r WHERE r.uuid =?",
                ps -> {
                    ps.setString(1, uuid);
                    ResultSet rs = ps.executeQuery();
                    checkForExistence(!rs.next(), uuid);
                    return new Resume(uuid, rs.getString("full_name"));
                });
    }

    @Override
    public void update(Resume r) {
        // sqlHelper.doCommonCode("UPDATE resume SET full_name = ? WHERE uuid IN (?)",
        sqlHelper.doCommonCode("UPDATE resume SET full_name = ? WHERE uuid = ?",
                ps -> {
                    String uuid = r.getUuid();
                    ps.setString(1, r.getFullName());
                    ps.setString(2, uuid);
                    checkForExistence(ps.executeUpdate() != 1, uuid);
                    return null;
                });
    }

    @Override
    public void save(Resume r) {
        sqlHelper.doCommonCode("INSERT INTO resume (uuid, full_name) VALUES (?,?)",
                ps -> {
                    ps.setString(1, r.getUuid());
                    ps.setString(2, r.getFullName());
                    ps.execute();
                    return null;
                });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.doCommonCode("DELETE FROM resume WHERE uuid=?",
                ps -> {
                    ps.setString(1, uuid);
                    checkForExistence(ps.executeUpdate() != 1, uuid);
                    return null;
                });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.doCommonCode("SELECT * FROM resume ORDER BY full_name,uuid",
                ps -> {
                    List<Resume> resumes = new ArrayList<>();
                    ResultSet rs = ps.executeQuery();
                    while (rs.next())
                        resumes.add(new Resume(rs.getString("uuid"), rs.getString("full_name")));
                    return resumes;
                });
    }

    @Override
    public int size() {
        return sqlHelper.doCommonCode("SELECT COUNT(*) FROM resume",
                ps -> {
                    ResultSet rs = ps.executeQuery();
                    return rs.next() ? rs.getInt("count") : 0;
                });
    }

    private void checkForExistence(Boolean notExist, String uuid) {
        if (notExist)
            throw new NotExistStorageException(uuid);
    }
}