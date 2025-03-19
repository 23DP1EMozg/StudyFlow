package com.example.eksamens_vm.exceptions;

public class UserAlreadyInRoomException extends Exception {
    public UserAlreadyInRoomException(String message) {
        super(message);
    }
}
