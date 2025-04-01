package com.example.eksamens_vm.services;

import com.example.eksamens_vm.enums.UserRole;
import com.example.eksamens_vm.exceptions.GroupNotFoundException;
import com.example.eksamens_vm.exceptions.RoomNotFoundException;
import com.example.eksamens_vm.exceptions.UserNotFoundException;
import com.example.eksamens_vm.models.Group;
import com.example.eksamens_vm.models.Room;
import com.example.eksamens_vm.models.RoomUser;
import com.example.eksamens_vm.models.User;

import java.util.ArrayList;
import java.util.List;

public class FilterService {
    JsonService jsonService = new JsonService();
    UserService userService = new UserService();
    RoomService roomService = new RoomService();
    GroupService groupService = new GroupService();

    public List<User> getAllUsersInGroup(String groupName, int roomId) throws GroupNotFoundException, UserNotFoundException, RoomNotFoundException {
        Room room = roomService.getRoomById(roomId);
        Group groupByName = groupService.getRoomGroupByName(groupName, roomId);
        List<RoomUser> roomUsers = room.getUsers()
                .stream()
                .filter(u -> u.getGroup() == groupByName.getId())
                .toList();

        List<User> users = new ArrayList<>();

        for(int i = 0; i<roomUsers.size(); i++){
            User user = userService.getUserById(roomUsers.get(i).getUser());
            users.add(user);
        }
        return users;


    }

    public List<String> getAllUsersInGroupNames(String groupName, int roomId) throws UserNotFoundException, GroupNotFoundException, RoomNotFoundException {

        Room room = roomService.getRoomById(roomId);
        Group groupByName = groupService.getRoomGroupByName(groupName, roomId);
        List<RoomUser> roomUsers = room.getUsers()
                .stream()
                .filter(u -> u.getGroup() == groupByName.getId())
                .toList();

        List<String> users = new ArrayList<>();

        for(int i = 0; i<roomUsers.size(); i++){
            User user = userService.getUserById(roomUsers.get(i).getUser());
            users.add(user.getUsername());
        }
        return users;
    }

    public List<User> getAllTeachersInRoom(int roomId) throws RoomNotFoundException, UserNotFoundException {
        Room room = roomService.getRoomById(roomId);

        List<User> users = new ArrayList<>();
        for(int i = 0; i<room.getUsers().size(); i++){
            User user = userService.getUserById(room.getUsers().get(i).getUser());
            if(user.getUserType() == UserRole.TEACHER){
                users.add(user);
            }
        }
        return users;
    }

    public List<String> getAllTeachersInRoomNames(int roomId) throws RoomNotFoundException, UserNotFoundException {
        Room room = roomService.getRoomById(roomId);

        List<String> users = new ArrayList<>();
        for(int i = 0; i<room.getUsers().size(); i++){
            User user = userService.getUserById(room.getUsers().get(i).getUser());
            if(user.getUserType() == UserRole.TEACHER){
                users.add(user.getUsername());
            }
        }
        return users;
    }

    public List<User> getAllStudentsInRoom(int roomId) throws RoomNotFoundException, UserNotFoundException {
        Room room = roomService.getRoomById(roomId);

        List<User> users = new ArrayList<>();
        for(int i = 0; i<room.getUsers().size(); i++){
            User user = userService.getUserById(room.getUsers().get(i).getUser());
            if(user.getUserType() == UserRole.STUDENT){
                users.add(user);
            }
        }
        return users;
    }

    public List<String> getAllStudentsInRoomNames(int roomId) throws RoomNotFoundException, UserNotFoundException {
        Room room = roomService.getRoomById(roomId);

        List<String> users = new ArrayList<>();
        for(int i = 0; i<room.getUsers().size(); i++){
            User user = userService.getUserById(room.getUsers().get(i).getUser());
            if(user.getUserType() == UserRole.STUDENT){
                users.add(user.getUsername());
            }
        }
        return users;
    }
}
