package com.example.eksamens_vm.services;


import com.example.eksamens_vm.exceptions.*;
import com.example.eksamens_vm.models.Group;
import com.example.eksamens_vm.models.Room;
import com.example.eksamens_vm.models.RoomUser;
import com.example.eksamens_vm.models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GroupService {

    JsonService jsonService = new JsonService();
    RoomService roomService = new RoomService();


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

        Group group = new Group(name, roomId, generateGroupId(), 0);
        jsonService.save(group, "groups.json", Group.class);
    }


    public List<String> getAllRoomGroupNames(int roomId){
        List<Group> groups = jsonService.getAll("groups.json", Group.class);
        List<Group> roomGroups = groups.stream().filter(g -> g.getRoomId() == roomId).toList();
        return roomGroups.stream().map(Group::getName).collect(Collectors.toList());
    }

    public List<Group> getAllGroupsInRoom(int roomId){
        List<Group> groups = jsonService.getAll("groups.json", Group.class);
        return groups.stream().filter(g -> g.getRoomId() == roomId).toList();
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

    public void addUserToGroup(int groupId, int userId) throws GroupNotFoundException, RoomNotFoundException, UserNotFoundException {
        Group group = getGroupById(groupId);
        Room room = roomService.getRoomById(group.getRoomId());

        List<RoomUser> roomUsers = room.getUsers();
        List<Room> rooms = jsonService.getAll("rooms.json", Room.class);
        List<Group> groups = jsonService.getAll("groups.json", Group.class);

        RoomUser foundUser = roomUsers.stream()
                .filter(u -> u.getUser() == userId)
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (foundUser.getGroup() != 0) {
            Group previousGroup = getGroupById(foundUser.getGroup());
            previousGroup.setUsersCount(previousGroup.getUsersCount() - 1);

            for (int i = 0; i < groups.size(); i++) {
                if (groups.get(i).getId() == previousGroup.getId()) {
                    groups.set(i, previousGroup);
                    System.out.println("b");
                }
            }
        }

        group.setUsersCount(group.getUsersCount() + 1);

        foundUser.setGroup(groupId);

        for (int i = 0; i < groups.size(); i++) {
            if (groups.get(i).getId() == groupId) {
                groups.set(i, group);
                System.out.println("a");
            }
        }

        for (int i = 0; i < roomUsers.size(); i++) {
            if (roomUsers.get(i).getUser() == userId) {
                roomUsers.set(i, foundUser);
                break;
            }
        }

        jsonService.saveMany(groups, "groups.json");

        rooms.stream()
                .filter(r -> r.getId() == group.getRoomId())
                .findFirst()
                .ifPresent(r -> r.setUsers(roomUsers));

        jsonService.saveMany(rooms, "rooms.json");
    }
    public String getUsersGroupinRoom(int userId, int roomId) throws RoomNotFoundException, UserNotFoundException, GroupNotFoundException {
        Room room = roomService.getRoomById(roomId);
        List<RoomUser> roomUsers = room.getUsers();

        RoomUser roomUser = roomUsers
                .stream()
                .filter(r -> r.getUser() == userId)
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException("user not found!"));

        if(roomUser.getGroup() == 0){
            return "Not in a group";
        }

        return getGroupById(roomUser.getGroup()).getName();
    }

    public Group getUsersGroupInRoom(int userId, int roomId) throws GroupNotFoundException, RoomNotFoundException, UserNotInGroupException {
        Room room = roomService.getRoomById(roomId);
        List<RoomUser> roomUsers = room.getUsers();
        RoomUser roomUser = roomUsers.stream().filter(r -> r.getUser() == userId).findFirst().get();
        if(roomUser.getGroup() == 0){
            throw new UserNotInGroupException("user doesnt belong to a group");
        }
        return getGroupById(roomUser.getGroup());
    }



}
