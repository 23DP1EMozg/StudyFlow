package com.example.eksamens_vm.data;


import com.example.eksamens_vm.models.Room;
import com.example.eksamens_vm.models.User;

public class Session {
    private User user;
    private Room room;
    private static Session instance;

    public Session() {
        user = null;
        room = null;
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


    public void setJoinedRoom(Room room) {
        this.room = room;
    }

    public Room getJoinedRoom() {
        return room;
    }

}
