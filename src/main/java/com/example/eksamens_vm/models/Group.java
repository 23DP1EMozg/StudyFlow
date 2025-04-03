package com.example.eksamens_vm.models;

public class Group {
    int id;
    private String name;
    private int roomId;
    private int usersCount;

    public Group(String name, int roomId, int id, int usersCount) {
        this.id = id;
        this.name = name;
        this.roomId = roomId;
        this.usersCount = usersCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUsersCount() {
        return usersCount;
    }

    public void setUsersCount(int usersCount) {
        this.usersCount = usersCount;
    }
}
