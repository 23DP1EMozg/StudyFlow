package com.example.eksamens_vm.data;


import com.example.eksamens_vm.models.User;

public class Session {
    private User user;
    private static Session instance;

    public Session() {
        user = null;
    }

    public static Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }

    public void setLoggedInUser(User loggedInUser) {
        user = loggedInUser;
    }
    public User getLoggedInUser(){
        return  user;
    }



}
