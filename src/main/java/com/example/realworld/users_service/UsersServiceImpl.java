package com.example.realworld.users_service;

import com.example.realworld.users_service.custom_exceptions.AlreadyExistsException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
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

    public User registerUser(String username, String email, String password)
            throws AlreadyExistsException {
        var id = UUID.randomUUID();
        var passwordHash = passwordEncoder.encode(password);
        var now = Timestamp.from(Instant.now());

        var user = new User(id, username, email, passwordHash, null, null, now, now);

        var sql =
                String.format(
                        "INSERT into %s(id, username, email, password_hash, created_at, updated_at)"
                                + " VALUES (?, ?, ?, ?, ?, ?)",
                        usersTable);

        try {
            jdbcTemplate.update(
                    sql,
                    user.getId().toString(),
                    user.getUsername(),
                    user.getEmail(),
                    user.getPasswordHash(),
                    user.getCreatedAt(),
                    user.getUpdatedAt());
        } catch (DuplicateKeyException e) {
            throw new AlreadyExistsException("User already exists");
        }

        return user;
    }

    public Optional<User> getUserByEmail(String email) {
        var sql = String.format("SELECT * FROM %s WHERE email = ?", usersTable);

        try {
            var user = jdbcTemplate.queryForObject(sql, new UserRowMapper(), email);

            if (user == null) {
                throw new NoSuchElementException("User not found");
            }

            return Optional.of(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public boolean verifyPassword(String email, String password) {
        var user = getUserByEmail(email);

        if (user.isEmpty()) {
            throw new NoSuchElementException("User not found");
        }

        return passwordEncoder.matches(password, user.get().getPasswordHash());
    }
}
