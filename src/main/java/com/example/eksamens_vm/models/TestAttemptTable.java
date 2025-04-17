package com.example.eksamens_vm.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TestAttemptTable {
    private StringProperty student;
    private StringProperty grade;
    private StringProperty percentage;

    public TestAttemptTable(String student, int grade, double percentage) {
        this.student = new SimpleStringProperty(student);
        this.grade = new SimpleStringProperty(String.valueOf(grade));
        this.percentage = new SimpleStringProperty(String.valueOf(percentage));
    }

    public String getStudent() {
        return student.get();
    }
    public void setStudent(String student) {
        this.student.set(student);
    }
    public StringProperty getStudentProperty() {
        return student;
    }


    public int getGrade() {
        return Integer.parseInt(grade.get());
    }
    public void setGrade(int grade) {
        this.grade.set(String.valueOf(grade));
    }

    public StringProperty getGradeProperty() {
        return grade;
    }

    public double getPercentage() {
        return Double.parseDouble(percentage.get());
    }

    public void setPercentage(double percentage) {
        this.percentage.set(String.valueOf(percentage));
    }

    public StringProperty getPercentageProperty() {
        return percentage;
    }


}
