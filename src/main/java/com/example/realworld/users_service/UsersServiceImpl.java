package com.example.realworld.users_service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsersServiceImpl implements UsersService {
    private final String usersTable = "users";

    private final PasswordEncoder passwordEncoder;

    @Autowired JdbcTemplate jdbcTemplate;

    public UsersServiceImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(String username, String email, String password) {
        var id = UUID.randomUUID();
        var passwordHash = passwordEncoder.encode(password);
        var now = Timestamp.from(Instant.now());

        var user = new User(id, username, email, passwordHash, now, now);

        var sql =
                String.format(
                        "INSERT into %s(id, username, email, password_hash, created_at, updated_at)"
                                + " VALUES (?, ?, ?, ?, ?, ?)",
                        usersTable);

        jdbcTemplate.update(
                sql,
                user.getId().toString(),
                user.getUsername(),
                user.getEmail(),
                user.getPasswordHash(),
                user.getCreatedAt(),
                user.getUpdatedAt());

        return user;
    }
}
