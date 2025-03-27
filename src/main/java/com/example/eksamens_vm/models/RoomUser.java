package com.example.eksamens_vm.models;

public class RoomUser {
    private int user;
    private int group;

    public RoomUser(int user, int group) {
        this.user = user;
        this.group = group;
    }


    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }
}
