package com.example.eksamens_vm.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class RecentTestTeacherTable {
    private StringProperty name;
    private StringProperty group;

    public RecentTestTeacherTable(String name, String group) {
        this.name = new SimpleStringProperty(name);
        this.group = new SimpleStringProperty(group);
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

    public String getGroup() {
        return group.get();
    }

    public void setGroup(String group) {
        this.group.set(group);
    }

    public StringProperty getGroupProperty() {
        return group;
    }
}
