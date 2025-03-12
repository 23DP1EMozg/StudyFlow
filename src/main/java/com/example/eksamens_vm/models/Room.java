package com.example.eksamens_vm.models;

import java.util.List;

public class Room {
    private int id;
    private String name;
    private List<Integer> students;
    private int owner;
    private List<Integer> joinRequests;
    private int joinCode;

    public Room(List<Integer> students, int id, String name, int owner, List<Integer> joinRequests, int joinCode) {
        this.students = students;
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

    public List<Integer> getStudents() {
        return students;
    }

    public void setStudents(List<Integer> students) {
        this.students = students;
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
