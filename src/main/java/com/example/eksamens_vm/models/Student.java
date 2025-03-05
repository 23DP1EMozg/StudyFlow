package com.example.eksamens_vm.models;

import com.example.eksamens_vm.enums.UserRole;

public class Student extends User{
    private String group;
    public Student(int id,
                   String username,
                   String password,
                   UserRole role,
                   String group) {
        super(id, username, password, role);
        this.group = group;
    }


    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
