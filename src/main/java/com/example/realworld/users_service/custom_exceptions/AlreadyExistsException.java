package com.example.realworld.users_service.custom_exceptions;

public class AlreadyExistsException extends Exception {
    public AlreadyExistsException(String errorMessage) {
        super(errorMessage);
    }
}
