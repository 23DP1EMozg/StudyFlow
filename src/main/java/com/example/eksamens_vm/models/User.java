package com.example.eksamens_vm.models;

import com.example.eksamens_vm.enums.UserRole;
import com.example.eksamens_vm.services.UserService;

public class User {

    private String username;
    private String password;
    private UserRole userType;
    private int id;



    public User(int id, String username, String password, UserRole userRole) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.userType = userRole;

    }

    public UserRole getUserRole() {
        return userType;
    }

    public void setUserRole(UserRole userType) {
        this.userType = userType;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", userType=" + userType +
                ", id=" + id +
                '}';
    }
}
