package com.example.eksamens_vm.exceptions;

public class UserNotInGroupException extends Exception {
    public UserNotInGroupException(String message) {
        super(message);
    }
}
