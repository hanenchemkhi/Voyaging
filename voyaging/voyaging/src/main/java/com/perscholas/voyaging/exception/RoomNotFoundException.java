package com.perscholas.voyaging.exception;

public class RoomNotFoundException extends RuntimeException {
    public RoomNotFoundException(Long id) {
        super("Room with " + id + " does not exist ");
    }

}
