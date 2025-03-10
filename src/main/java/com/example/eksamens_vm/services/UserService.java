package com.example.eksamens_vm.services;

import com.example.eksamens_vm.exceptions.NotFoundException;
import com.example.eksamens_vm.exceptions.UserNotFoundException;
import com.example.eksamens_vm.models.Room;
import com.example.eksamens_vm.models.User;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class UserService {

    private JsonService jsonService = new JsonService();

    public int generateNewId(){
         List<User> users = jsonService.getAll("users.json", User.class);
         return users.isEmpty() ? 1 : users.getLast().getId() + 1;

    }

    public boolean userExists(String username) {
        List<User> users = jsonService.getAll("users.json", User.class);
        System.out.println(users);
        return users.stream().anyMatch(user -> user.getUsername().equals(username));
    }

    public User getUserById(int id) throws UserNotFoundException {
        List<User> users = jsonService.getAll("users.json", User.class);
        return users.stream().filter(user -> user.getId() == id).findFirst()
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    public User getUserByUsername(String username){
        List<User> users = jsonService.getAll("users.json", User.class);
        return users.stream().filter(usr -> usr.getUsername().equals(username))
                .findFirst()
                .orElse(null);

    }

    public List<String> getAllRoomNames(User user){
        List<String> names = new ArrayList<>();


        for(int i = 0; i<user.getRooms().size(); i++){
            names.add(user.getRooms().get(i).getName());
        }
        return names;
    }



    public void addRoom(User user, Room room) throws NotFoundException {
        List<Room> rooms = user.getRooms();
        List<User> users = jsonService.getAll("users.json", User.class);
        rooms.add(room);
        user.setRooms(rooms);

        for(int i = 0; i<users.size(); i++){
            if(users.get(i).getId() == user.getId()){
                users.set(i, user);
                jsonService.saveMany(users, "users.json");
                return;
            }
        }
        throw new NotFoundException("not found!");
    }
}
