package com.example.eksamens_vm.models;

import com.example.eksamens_vm.enums.TestStatus;

public class Test {
    private int id;
    private String name;
    private int groupId;
    private int roomId;
    private int teacherId;
    private TestStatus testStatus;

    public Test(int id, int teacherId, int roomId, int groupId, String name, TestStatus testStatus) {
        this.id = id;
        this.teacherId = teacherId;
        this.roomId = roomId;
        this.groupId = groupId;
        this.name = name;
        this.testStatus = testStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TestStatus getTestStatus() {
        return testStatus;
    }

    public void setTestStatus(TestStatus testStatus) {
        this.testStatus = testStatus;
    }
}
