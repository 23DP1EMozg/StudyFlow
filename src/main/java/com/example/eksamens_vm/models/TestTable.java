package com.example.eksamens_vm.models;

import com.example.eksamens_vm.enums.TestStatus;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TestTable {

    private StringProperty name;
    private StringProperty group;
    private StringProperty teacher;
    private StringProperty status;

    public TestTable(String name, TestStatus status, String teacher, String group) {
        this.name = new SimpleStringProperty(name);
        this.status = new SimpleStringProperty(status.toString());
        this.teacher = new SimpleStringProperty(teacher);
        this.group = new SimpleStringProperty(group);
    }

    public String getName(){
        return name.get();
    }

    public void setName(String name){
        this.name.set(name);
    }

    public StringProperty getNameProperty(){
        return name;
    }

    public String getGroup(){
        return group.get();
    }

    public void setGroup(String group){
        this.group.set(group);
    }

    public StringProperty getGroupProperty(){
        return group;
    }

    public String getTeacher(){
        return teacher.get();
    }

    public void setTeacher(String teacher){
        this.teacher.set(teacher);
    }

    public StringProperty getTeacherProperty(){
        return teacher;
    }

    public String getStatus(){
        return status.get();
    }

    public void setStatus(String status){
        this.status.set(status);
    }

    public StringProperty getStatusProperty(){
        return status;
    }
}
