package com.perscholas.voyaging.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String email) {
        super("User with " + email + " does not exist ");
    }
}
