package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.sql.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public record SqlHelper(ConnectionFactory connectionFactory) {

    public Object doCommonCode(String sqlRequest, UniqueCode uniqueCode) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sqlRequest)) {
            return uniqueCode.execute(ps);
        } catch (SQLException e) {
            if (e.getSQLState().equals("23505"))
                throw new ExistStorageException(e);
            throw new StorageException(e);
        }
    }

    @FunctionalInterface
    public interface UniqueCode {
        Object execute(PreparedStatement ps) throws SQLException;
    }
}