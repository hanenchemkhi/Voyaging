package com.perscholas.voyaging.exception;

public class CustomerWithEmailExistException extends RuntimeException {
    public CustomerWithEmailExistException(String email) {
        super("Customer with " + email + " already exists ");

    }
}
