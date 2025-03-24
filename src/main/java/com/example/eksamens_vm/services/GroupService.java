package com.example.eksamens_vm.services;


import com.example.eksamens_vm.models.Group;

public class GroupService {

    JsonService jsonService = new JsonService();

    public void createGroup(String name, int roomId){
        Group group = new Group(name, roomId);
        jsonService.save(group, "groups.json", Group.class);
    }

}
