package com.example.realworld.users_service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import org.springframework.jdbc.core.RowMapper;

public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new User(
                UUID.fromString(rs.getString("id")),
                rs.getString("username"),
                rs.getString("email"),
                rs.getString("password_hash"),
                rs.getBlob("bio"),
                rs.getString("image"),
                rs.getTimestamp("created_at"),
                rs.getTimestamp("updated_at"));
    }
}
