package com.urise.webapp.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public record SqlHelper(ConnectionFactory connectionFactory) {

    public <T> T doCommonCode(String sqlRequest, UniqueCode<T> uniqueCode) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sqlRequest)) {
            return uniqueCode.execute(ps);
        } catch (SQLException e) {
            throw ExceptionUtil.convertException(e);
        }
    }

    @FunctionalInterface
    public interface UniqueCode<T> {
        T execute(PreparedStatement ps) throws SQLException;
    }
}