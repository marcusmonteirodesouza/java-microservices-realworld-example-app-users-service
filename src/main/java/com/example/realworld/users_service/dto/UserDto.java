package com.example.realworld.users_service.dto;

import com.example.realworld.users_service.User;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.WRAPPER_OBJECT;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

@JsonTypeName("user")
@JsonTypeInfo(include = WRAPPER_OBJECT, use = NAME)
public class UserDto {
    private final String email;
    private final String token;
    private final String username;
    private final String bio;
    private final String image;

    public static UserDto fromUserAndToken(User user, String token) {
        return new UserDto(user.getEmail(), token, user.getUsername(), user.getBio(), user.getImage());
    }

    private UserDto(String email, String token, String username, String bio, String image) {
        this.email = email;
        this.token = token;
        this.username = username;
        this.bio = bio;
        this.image = image;
    }

    public String getEmail() {
        return email;
    }

    public String getToken() {
        return token;
    }

    public String getUsername() {
        return username;
    }

    public String getBio() {
        return bio;
    }

    public String getImage() {
        return image;
    }
}
