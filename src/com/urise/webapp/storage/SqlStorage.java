package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.sql.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements Storage {
    public final ConnectionFactory connectionFactory;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void clear() {
        SqlHelper<Object> sqlHelper = new SqlHelper<>();
        sqlHelper.doCommonCode("DELETE FROM resumes.public.resume", PreparedStatement::execute);
    }

    @Override
    public Resume get(String uuid) {
        SqlHelper<Resume> sqlHelper = new SqlHelper<>();
        return sqlHelper.doCommonCode("SELECT * FROM resumes.public.resume r WHERE r.uuid =?",
                ps -> {
                    ps.setString(1, uuid);
                    ResultSet rs = ps.executeQuery();
                    checkForExistence(!rs.next(), uuid);
                    return new Resume(uuid, rs.getString("full_name"));
                });
    }

    @Override
    public void update(Resume r) {
        SqlHelper<Object> sqlHelper = new SqlHelper<>();
        sqlHelper.doCommonCode("UPDATE resumes.public.resume SET full_name = ? WHERE uuid IN (?)",
                ps -> {
                    ps.setString(1, r.getFullName());
                    ps.setString(2, r.getUuid());
                    checkForExistence(ps.executeUpdate() != 1, r.getUuid());
                    return null;
                });
    }

    @Override
    public void save(Resume r) {
        SqlHelper<Object> sqlHelper = new SqlHelper<>();
        sqlHelper.doCommonCode("INSERT INTO resumes.public.resume (uuid, full_name) VALUES (?,?)",
                ps -> {
                    ps.setString(1, r.getUuid());
                    ps.setString(2, r.getFullName());
                    ps.execute();
                    return null;
                });
    }

    @Override
    public void delete(String uuid) {
        SqlHelper<Object> sqlHelper = new SqlHelper<>();
        sqlHelper.doCommonCode("DELETE FROM resumes.public.resume WHERE uuid=?",
                ps -> {
                    ps.setString(1, uuid);
                    checkForExistence(ps.executeUpdate() != 1, uuid);
                    return null;
                });
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> resumes = new ArrayList<>();
        SqlHelper<List<Resume>> sqlHelper = new SqlHelper<>();
        return sqlHelper.doCommonCode("SELECT * FROM resumes.public.resume ORDER BY uuid ASC",
                ps -> {
                    ResultSet rs = ps.executeQuery();
                    while (rs.next())
                        resumes.add(new Resume(rs.getString("uuid").trim(), rs.getString("full_name")));
                    return resumes;
                });
    }

    @Override
    public int size() {
        SqlHelper<Integer> sqlHelper = new SqlHelper<>();
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

    @FunctionalInterface
    public interface ABlockOfCode<T> {
        T execute(PreparedStatement ps) throws SQLException;
    }

    public class SqlHelper<T> {

        public T doCommonCode(String sqlRequest, ABlockOfCode<T> aBlockOfCode) {
            try (Connection conn = connectionFactory.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sqlRequest)) {
                return aBlockOfCode.execute(ps);
            } catch (SQLException e) {
                if (e.getSQLState().equals("23505"))
                    throw new ExistStorageException(e);
                throw new StorageException(e);
            }
        }
    }
}