package com.example.eksamens_vm.models;

import com.example.eksamens_vm.enums.UserRole;

import java.util.List;

public class Teacher extends User{

    public Teacher(int id, String username, String password, UserRole userRole, List<Room> rooms) {
        super(id, username, password, userRole, rooms);
    }
}
