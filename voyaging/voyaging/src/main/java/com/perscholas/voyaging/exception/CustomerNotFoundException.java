package com.perscholas.voyaging.exception;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(Long id) {

        super("Customer with " + id + " does not exist ");
    }
}
