package com.example.eksamens_vm.services;

import com.example.eksamens_vm.data.Session;
import com.example.eksamens_vm.exceptions.InputFieldEmptyException;
import com.example.eksamens_vm.exceptions.RoomNotFoundException;
import com.example.eksamens_vm.models.Room;
import com.example.eksamens_vm.models.Student;

import java.util.ArrayList;
import java.util.List;

public class RoomService {
    Session session;
    JsonService jsonService;
    public RoomService() {
        this.session = Session.getInstance();
        this.jsonService = new JsonService();
    }

    public void createRoom(String name) throws InputFieldEmptyException {
        if(name.isBlank()){
            throw new InputFieldEmptyException("cannot leave fields empty!");
        }

        Room room = new Room(
                List.of(),
                generateRoomId(),
                name,
                session.getLoggedInUser().getId()
        );

        jsonService.save(room, "rooms.json", Room.class);

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

    public void addStudent(Student student, int roomId){

    }
}
