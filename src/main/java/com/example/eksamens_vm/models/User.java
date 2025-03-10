package com.example.eksamens_vm.models;

import com.example.eksamens_vm.enums.UserRole;

import java.util.List;

public class User {

    private String username;
    private String password;
    private UserRole userType;
    private int id;
    private List<Room> rooms;


    public User(int id, String username, String password, UserRole userRole, List<Room> rooms) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.userType = userRole;
        this.rooms = rooms;

    }

    public UserRole getUserType() {
        return userType;
    }

    public void setUserType(UserRole userType) {
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

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
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
