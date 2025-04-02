package com.example.eksamens_vm.models;

import com.example.eksamens_vm.enums.UserRole;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class UserTable {
    private StringProperty username;
    private StringProperty group;
    private StringProperty role;

    public UserTable(String group, UserRole role, String username) {
        this.group = new SimpleStringProperty(group);
        this.role = new SimpleStringProperty(role.toString());
        this.username = new SimpleStringProperty(username);
    }

    public String getUsername() {
        return username.get();
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public StringProperty getUsernameProperty() {
        return username;
    }

    public String getRole() {
        return role.get();
    }

    public void setRole(UserRole role) {
        this.role.set(role.toString());
    }

    public StringProperty getRoleProperty() {
        return role;
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
