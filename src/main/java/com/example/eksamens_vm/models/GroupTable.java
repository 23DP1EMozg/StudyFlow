package com.example.eksamens_vm.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class GroupTable {
    private StringProperty name;
    private StringProperty usersCount;

    public GroupTable(String name, String usersCount) {
        this.name = new SimpleStringProperty(name);
        this.usersCount = new SimpleStringProperty(usersCount);
    }

    public String getName() {
        return name.get();
    }
    public void setName(String name) {
        this.name.set(name);
    }

    public String getUsersCount() {
        return usersCount.get();
    }

    public void setUsersCount(String usersCount) {
        this.usersCount.set(usersCount);
    }

    public StringProperty getNameProperty() {
        return name;
    }

    public StringProperty getUsersCountProperty() {
        return usersCount;
    }


}
