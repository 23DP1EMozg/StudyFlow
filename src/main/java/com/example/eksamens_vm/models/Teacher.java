package com.example.eksamens_vm.models;

import com.example.eksamens_vm.enums.UserRole;

public class Teacher extends User{

    public Teacher(int id,String username, String password, UserRole userRole) {
        super(id, username, password, userRole);
    }
}
