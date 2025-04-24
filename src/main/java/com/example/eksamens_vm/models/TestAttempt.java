package com.example.eksamens_vm.models;

public class TestAttempt {
    private int userId;
    private int testId;
    private double percent;
    private int grade;
    private int roomId;


    public TestAttempt(int userId, int grade, int testId, double percent, int roomId) {
        this.userId = userId;
        this.grade = grade;
        this.testId = testId;
        this.percent = percent;
        this.roomId = roomId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }

    public int getTestId() {
        return testId;
    }

    public void setTestId(int testId) {
        this.testId = testId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }
}
