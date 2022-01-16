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
    SqlHelper sqlHelper = new SqlHelper();

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void clear() {
        sqlHelper.commonCode("DELETE FROM resumes.public.resume", PreparedStatement::execute);
     /*   try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM resumes.public.resume")) {
            ps.execute();
        } catch (SQLException e) {
            throw new StorageException(e);
        }*/
    }

    @Override
    public Resume get(String uuid) {
        Resume[] resume = new Resume[1];
        sqlHelper.commonCode("SELECT * FROM resumes.public.resume r WHERE r.uuid =?",
                ps -> {
                    ps.setString(1, uuid);
                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()) {
                        throw new NotExistStorageException(uuid);
                    }
                    resume[0] = new Resume(uuid, rs.getString("full_name"));
                });
        return resume[0];
   /*     try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM resumes.public.resume r WHERE r.uuid =?")) {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            return new Resume(uuid, rs.getString("full_name"));
        } catch (SQLException e) {
            throw new StorageException(e);
        }*/
    }

    @Override
    public void update(Resume r) {
        sqlHelper.commonCode("UPDATE resumes.public.resume SET full_name = ? WHERE uuid IN (?)",
                ps -> {
                    ps.setString(1, r.getFullName());
                    ps.setString(2, r.getUuid());
                    // ps.execute();
                    if (ps.executeUpdate() != 1)
                        throw new NotExistStorageException(r.getUuid());
                });
     /*   try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement("UPDATE resumes.public.resume SET full_name = ?" +
                     " WHERE uuid IN (?)")) {
            ps.setString(1, r.getFullName());
            ps.setString(2, r.getUuid());
            // ps.execute();
            if (ps.executeUpdate() != 1)
                throw new NotExistStorageException(r.getUuid());
        } catch (SQLException e) {
            throw new StorageException(e);
        }*/
    }

    @Override
    public void save(Resume r) {
        sqlHelper.commonCode("INSERT INTO resumes.public.resume (uuid, full_name) VALUES (?,?)",
                ps -> {
                    ps.setString(1, r.getUuid());
                    ps.setString(2, r.getFullName());
                    ps.execute();
                });
       /* try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement("INSERT INTO resumes.public.resume (uuid, full_name) VALUES (?,?)")) {
            ps.setString(1, r.getUuid());
            ps.setString(2, r.getFullName());
            ps.execute();
        } catch (PSQLException e) {
            if (e.getSQLState().equals("23505"))
                throw new ExistStorageException(r.getUuid());
        } catch (SQLException e) {
            throw new StorageException(e);
        }*/
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.commonCode("DELETE FROM resumes.public.resume WHERE uuid=?",
                ps -> {
                    ps.setString(1, uuid);
                    if (ps.executeUpdate() != 1)
                        throw new NotExistStorageException(uuid);
                });
    /*    try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM resumes.public.resume WHERE uuid=?")) {
            ps.setString(1, uuid);
            //   ps.execute();
            if (ps.executeUpdate() != 1)
                throw new NotExistStorageException(uuid);
        } catch (SQLException e) {
            throw new StorageException(e);
        }*/
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> resumes = new ArrayList<>();
        sqlHelper.commonCode("SELECT * FROM resumes.public.resume ORDER BY uuid ASC",
                ps -> {
                    ResultSet rs = ps.executeQuery();
                    while (rs.next())
                        resumes.add(new Resume(rs.getString("uuid").trim(), rs.getString("full_name")));
                });
        return resumes;
    /*    try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM resumes.public.resume ORDER BY uuid ASC")) {
            ResultSet rs = ps.executeQuery();
            while (rs.next())
                resumes.add(new Resume(rs.getString("uuid").trim(), rs.getString("full_name")));
            return resumes;
        } catch (SQLException e) {
            throw new StorageException(e);
        }*/
    }

    @Override
    public int size() {
        int[] count = new int[1];
        sqlHelper.commonCode("SELECT COUNT(*) FROM resume",
                ps -> {
                    ResultSet rs = ps.executeQuery();
                    count[0] = rs.next() ? rs.getInt("count") : 0;
                });
        return count[0];
      /*  try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) FROM resume")) {
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt("count") : 0;
        } catch (SQLException e) {
            throw new StorageException(e);
        }*/
    }

    @FunctionalInterface
    public interface ABlockOfCode {
        void execute(PreparedStatement ps) throws SQLException;
    }

    public class SqlHelper {

        public void commonCode(String sqlRequest, ABlockOfCode aBlockOfCode) {
            try (Connection conn = connectionFactory.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sqlRequest)) {
                aBlockOfCode.execute(ps);
            } catch (SQLException e) {
                if (e.getSQLState().equals("23505"))
                    throw new ExistStorageException(e);
                throw new StorageException(e);
            }
        }
    }
}



