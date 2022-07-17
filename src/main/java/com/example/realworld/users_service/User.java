package com.example.realworld.users_service;

import java.sql.Blob;
import java.sql.Timestamp;
import java.util.UUID;

public class User {
    private UUID id;

    private String username;

    private String email;

    private String passwordHash;
    private Blob bio;
    private String image;

    private Timestamp createdAt;

    private Timestamp updatedAt;

    public User(
            UUID id,
            String username,
            String email,
            String passwordHash,
            Blob bio,
            String image,
            Timestamp createdAt,
            Timestamp updatedAt) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public Blob getBio() {
        return bio;
    }

    public String getImage() {
        return image;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }
}
