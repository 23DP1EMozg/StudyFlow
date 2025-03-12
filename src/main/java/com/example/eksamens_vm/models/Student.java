package com.example.eksamens_vm.models;

import com.example.eksamens_vm.enums.UserRole;

import java.util.List;

public class Student extends User{
    private String group;
    public Student(int id,
                   String username,
                   String password,
                   UserRole role,
                   String group,
                   List<Integer> rooms) {
        super(id, username, password, role, rooms);
        this.group = group;
    }


    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
