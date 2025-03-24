package com.example.eksamens_vm.services;


import com.example.eksamens_vm.exceptions.GroupExistsException;
import com.example.eksamens_vm.models.Group;

import java.util.List;
import java.util.stream.Collectors;

public class GroupService {

    JsonService jsonService = new JsonService();

    public void createGroup(String name, int roomId) throws GroupExistsException {
        List<Group> groups = jsonService.getAll("groups.json", Group.class);

        for(int i = 0; i<groups.size(); i++){
            if(groups.get(i).getRoomId() == roomId && groups.get(i).getName().equalsIgnoreCase(name)){
                throw new GroupExistsException("group already exists!");
            }
        }

        Group group = new Group(name, roomId);
        jsonService.save(group, "groups.json", Group.class);
    }


    public List<String> getAllRoomGroupNames(int roomId){
        List<Group> groups = jsonService.getAll("groups.json", Group.class);
        List<Group> roomGroups = groups.stream().filter(g -> g.getRoomId() == roomId).toList();
        return roomGroups.stream().map(Group::getName).collect(Collectors.toList());
    }
}
