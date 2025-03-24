package com.example.eksamens_vm.models;

import java.util.List;

public class Room {
    private int id;
    private String name;
    private List<RoomUser> users;
    private int owner;
    private List<Integer> joinRequests;
    private int joinCode;

    public Room(List<RoomUser> users, int id, String name, int owner, List<Integer> joinRequests, int joinCode) {
        this.users = users;
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.joinRequests = joinRequests;
        this.joinCode = joinCode;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<RoomUser> getUsers() {
        return users;
    }

    public void setUsers(List<RoomUser> students) {
        this.users = students;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }

    public int getJoinCode() {
        return joinCode;
    }

    public void setJoinCode(int joinCode) {
        this.joinCode = joinCode;
    }

    public List<Integer> getJoinRequests() {
        return joinRequests;
    }

    public void setJoinRequests(List<Integer> joinRequests) {
        this.joinRequests = joinRequests;
    }
}
