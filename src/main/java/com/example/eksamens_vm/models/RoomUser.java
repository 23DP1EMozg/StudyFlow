package com.example.eksamens_vm.models;

public class RoomUser {
    private int user;
    private String group;

    public RoomUser(int user, String group) {
        this.user = user;
        this.group = group;
    }


    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }
}
