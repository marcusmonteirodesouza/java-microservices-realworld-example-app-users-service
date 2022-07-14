package com.example.realworld.users_service;

import java.util.UUID;

public class User {
    private UUID id;
    private String email;
    private String username;

    private String passwordHash;
    private String bio;
    private String image;

    public User(String email, String username, String passwordHash) {
        this.email = email;
        this.username = username;
        this.passwordHash = passwordHash;
    }

    public UUID getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getBio() {
        return bio;
    }

    public String getImage() {
        return image;
    }
}
