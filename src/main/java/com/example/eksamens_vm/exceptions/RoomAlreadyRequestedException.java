package com.example.eksamens_vm.exceptions;

public class RoomAlreadyRequestedException extends Exception {
    public RoomAlreadyRequestedException(String message) {
        super(message);
    }
}
