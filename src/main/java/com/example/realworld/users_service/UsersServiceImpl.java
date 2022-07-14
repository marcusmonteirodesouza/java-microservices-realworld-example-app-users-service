package com.example.realworld.users_service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsersServiceImpl implements UsersService {
    private final PasswordEncoder passwordEncoder;

    public UsersServiceImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(String username, String email, String password) {
        var passwordHash = passwordEncoder.encode(password);

        return new User(email, username, passwordHash);
    }
}
