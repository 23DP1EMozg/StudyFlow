package com.example.eksamens_vm.services;

import com.example.eksamens_vm.data.Session;
import com.example.eksamens_vm.exceptions.InputFieldEmptyException;
import com.example.eksamens_vm.exceptions.NotFoundException;
import com.example.eksamens_vm.exceptions.RoomNotFoundException;
import com.example.eksamens_vm.exceptions.UserNotFoundException;
import com.example.eksamens_vm.models.Room;
import com.example.eksamens_vm.models.Student;
import com.example.eksamens_vm.models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RoomService {
    Session session;
    JsonService jsonService;
    UserService userService;
    public RoomService() {
        this.session = Session.getInstance();
        this.jsonService = new JsonService();
        this.userService = new UserService();
    }



    public void createRoom(String name) throws InputFieldEmptyException {
        if(name.isBlank()){
            throw new InputFieldEmptyException("cannot leave fields empty!");
        }

        Room room = new Room(
                List.of(),
                generateRoomId(),
                name,
                session.getLoggedInUser().getId(),
                List.of(),
                generateJoinCode()
        );

        jsonService.save(room, "rooms.json", Room.class);
        try {
            addUserRoom(session.getLoggedInUser(), room);
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        }

    }

    private int generateRoomId(){
        List<Room> rooms = jsonService.getAll("rooms.json", Room.class);
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

    public Room getRoomById(int id) throws RoomNotFoundException {
        List<Room> rooms = jsonService.getAll("rooms.json", Room.class);
        return rooms.stream().filter(room -> room.getId() == id).findFirst()
                .orElseThrow(() -> new RoomNotFoundException("room not found!"));
    }

    public Room getRoomByName(String name) throws RoomNotFoundException {
        List<Room> rooms = jsonService.getAll("rooms.json", Room.class);
        return rooms.stream().filter(room -> room.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new RoomNotFoundException("room not found!"));
    }

    public void addStudent(Student student, int roomId){

    }

    private int getNewJoinCode(){
        Random random = new Random();
        return 10000000 + random.nextInt(90000000);
    }

    private boolean codeExists(int code){
        List<Room> rooms = jsonService.getAll("rooms.json", Room.class);
        for(int i = 0; i<rooms.size(); i++){
            if(rooms.get(i).getId() == code){
                return true;
            }
        }
        return false;
    }

    private int generateJoinCode(){
        int joinCode = getNewJoinCode();

        while(codeExists(joinCode)){
            joinCode = getNewJoinCode();
        }

        return joinCode;

    }

    public Room getRoomByJoinCode(int joinCode) throws RoomNotFoundException {
        List<Room> rooms = jsonService.getAll("rooms.json", Room.class);
        return rooms.stream().filter(r -> r.getJoinCode() == joinCode)
                .findFirst()
                .orElseThrow(() -> new RoomNotFoundException("room not found!"));
    }

    public Room requestRoom(int joinCode, User user) throws RoomNotFoundException {
        Room room = getRoomByJoinCode(joinCode);
        List<Integer> roomRequests = room.getJoinRequests();
        List<Room> allRooms = jsonService.getAll("rooms.json", Room.class);
        roomRequests.add(user.getId());
        room.setJoinRequests(roomRequests);

        for(int i = 0; i<allRooms.size(); i++){
            if(allRooms.get(i).getId() == room.getId()){
                allRooms.set(i, room);
                jsonService.saveMany(allRooms, "rooms.json");
                return room;
            }
        }

        throw new RoomNotFoundException("room not found!");
    }

    public List<String> getAllRequestsNames(int roomId) throws RoomNotFoundException, UserNotFoundException {
        Room room = getRoomById(roomId);
        List<String> names = new ArrayList<>();
        List<User> users = new ArrayList<>();

        for(int i = 0; i<room.getJoinRequests().size(); i++){
            users.add(userService.getUserById(room.getJoinRequests().get(i)));
        }

        for(int i = 0; i<users.size(); i++){
            names.add(users.get(i).getUsername());
        }

        return names;
    }

    public void removeUserFromRequest(int userId, int roomId) throws RoomNotFoundException, UserNotFoundException {
        Room room = getRoomById(roomId);
        List<Room> rooms = jsonService.getAll("rooms.json", Room.class);
        List<Integer> roomRequests = room.getJoinRequests();


        roomRequests.removeIf(r -> r == userId);
        room.setJoinRequests(roomRequests);

        for(int i = 0; i<rooms.size(); i++){
            if(rooms.get(i).getId() == roomId){
                rooms.set(i, room);
                jsonService.saveMany(rooms, "rooms.json");
                return;
            }
        }

    }

    public void addUserRoom(User user, Room room) throws NotFoundException {
        List<Integer> rooms = user.getRooms();
        List<User> users = jsonService.getAll("users.json", User.class);



        if (!rooms.contains(room)) {
            rooms.add(room.getId());
        }
        user.setRooms(rooms);

        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId() == user.getId()) {
                users.set(i, user);
                jsonService.saveMany(users, "users.json");
                return;
            }
        }


        throw new NotFoundException("User not found!");
    }


    public void acceptUserRequest(int userId, int roomId) throws RoomNotFoundException, NotFoundException, UserNotFoundException {
        Room room = getRoomById(roomId);
        User user = userService.getUserById(userId);
        List<Room> rooms = jsonService.getAll("rooms.json", Room.class);
        addUserRoom(user, room);
        List<Integer> roomUsers = room.getStudents();
        roomUsers.add(user.getId());
        room.setStudents(roomUsers);

        List<Integer> roomRequests = room.getJoinRequests();
        roomRequests.removeIf(r -> r == userId);
        room.setJoinRequests(roomRequests);


        removeUserFromRequest(user.getId(), roomId);
        for(int i = 0; i<rooms.size(); i++){
            if(rooms.get(i).getId() == roomId){
                rooms.set(i, room);
                jsonService.saveMany(rooms, "rooms.json");
                return;
            }
        }

    }

    public void rejectUserRequest(int userId, int roomId) throws RoomNotFoundException, NotFoundException, UserNotFoundException {
        User user = userService.getUserById(userId);
        removeUserFromRequest(user.getId(), roomId);
    }

    public List<String> getAllUserRoomNames(User user) throws RoomNotFoundException {
        List<String> names = new ArrayList<>();
        List<Room> rooms = jsonService.getAll("rooms.json", Room.class);

        for(int i = 0; i<user.getRooms().size(); i++){
            if(user.getRooms().get(i) == rooms.get(i).getId()){
                names.add(rooms.get(i).getName());
            }
        }
        return names;
    }

    public List<User> getAllUsersInRoom(int roomId) throws RoomNotFoundException, UserNotFoundException {
        List<User> users = new ArrayList<>();
        Room room = getRoomById(roomId);

        for(int i = 0; i<room.getStudents().size(); i++){
            User user = userService.getUserById(room.getStudents().get(i));
            users.add(user);
        }

        return users;
    }

    public List<String> getAllUsersInRoomNames(int roomId) throws RoomNotFoundException, UserNotFoundException {
        List<String> users = new ArrayList<>();
        Room room = getRoomById(roomId);

        for(int i = 0; i<room.getStudents().size(); i++){
            String user = userService.getUserById(room.getStudents().get(i)).getUsername();
            users.add(user);
        }

        return users;
    }

    public void removeUser(int userId) throws UserNotFoundException {
        List<User> users = jsonService.getAll("users.json", User.class);
        if(!userService.userExists(userId)){
            throw new UserNotFoundException("User doesnt exist");
        }

        users.removeIf(user -> user.getId() == userId);
        jsonService.saveMany(users, "users.json");


    }

}
