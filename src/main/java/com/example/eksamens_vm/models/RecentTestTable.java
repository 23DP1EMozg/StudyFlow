package com.example.eksamens_vm.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class RecentTestTable {
    private StringProperty name;
    private StringProperty grade;

    public RecentTestTable(String name, int grade) {
        this.name = new SimpleStringProperty(name);
        this.grade = new SimpleStringProperty(String.valueOf(grade));
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty getNameProperty() {
        return name;
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
}
