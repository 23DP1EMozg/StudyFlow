package com.example.eksamens_vm.services;


import com.example.eksamens_vm.exceptions.GroupExistsException;
import com.example.eksamens_vm.exceptions.GroupNotFoundException;
import com.example.eksamens_vm.models.Group;
import com.example.eksamens_vm.models.Room;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GroupService {

    JsonService jsonService = new JsonService();

    private int generateGroupId(){
        List<Room> rooms = jsonService.getAll("groups.json", Room.class);
        return rooms.isEmpty() ? 1 : rooms.getLast().getId() + 1;
    }

    public List<String> getAllRoomNames(){
        List<String> names = new ArrayList<>();
        List<Room> rooms = jsonService.getAll("rooms.json", Room.class);

        for(int i = 0; i<rooms.size(); i++){
            names.add(rooms.get(i).getName());
        }
        return names;
    }

    public void createGroup(String name, int roomId) throws GroupExistsException {
        List<Group> groups = jsonService.getAll("groups.json", Group.class);

        for(int i = 0; i<groups.size(); i++){
            if(groups.get(i).getRoomId() == roomId && groups.get(i).getName().equalsIgnoreCase(name)){
                throw new GroupExistsException("group already exists!");
            }
        }

        Group group = new Group(name, roomId, generateGroupId());
        jsonService.save(group, "groups.json", Group.class);
    }


    public List<String> getAllRoomGroupNames(int roomId){
        List<Group> groups = jsonService.getAll("groups.json", Group.class);
        List<Group> roomGroups = groups.stream().filter(g -> g.getRoomId() == roomId).toList();
        return roomGroups.stream().map(Group::getName).collect(Collectors.toList());
    }

    public Group getRoomGroupByName(String name, int roomId) throws GroupNotFoundException {
        List<Group> groups = jsonService.getAll("groups.json", Group.class);
        return groups.stream()
                .filter(g -> g.getName().equalsIgnoreCase(name) && g.getRoomId() == roomId)
                .findFirst()
                .orElseThrow(() -> new GroupNotFoundException("group not found"));
    }

    public Group getGroupById(int id) throws GroupNotFoundException {
        List<Group> groups = jsonService.getAll("groups.json", Group.class);
        return groups.stream()
                .filter(g -> g.getId() == id)
                .findFirst()
                .orElseThrow(() -> new GroupNotFoundException("group not found"));
    }


}
