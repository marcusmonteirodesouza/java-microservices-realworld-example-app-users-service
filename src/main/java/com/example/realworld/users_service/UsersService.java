package com.example.realworld.users_service;

import com.example.realworld.users_service.custom_exceptions.AlreadyExistsException;
import java.util.Optional;

public interface UsersService {
    public User registerUser(String username, String email, String password)
            throws AlreadyExistsException;

    public Optional<User> getUserByEmail(String email);

    public boolean verifyPassword(String email, String password);
}
