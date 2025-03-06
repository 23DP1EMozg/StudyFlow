package com.example.eksamens_vm.models;

import java.util.List;

public class Room {
    private int id;
    private String name;
    private List<User> students;
    private int owner;

    public Room(List<User> students, int id, String name, int owner) {
        this.students = students;
        this.id = id;
        this.name = name;
        this.owner = owner;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<User> getStudents() {
        return students;
    }

    public void setStudents(List<User> students) {
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
}
