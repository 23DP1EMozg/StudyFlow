package com.example.eksamens_vm.services;

import com.example.eksamens_vm.data.Session;
import com.example.eksamens_vm.exceptions.NotFoundException;
import com.example.eksamens_vm.exceptions.RoomNotFoundException;
import com.example.eksamens_vm.exceptions.UserNotFoundException;
import com.example.eksamens_vm.models.Group;
import com.example.eksamens_vm.models.Room;
import com.example.eksamens_vm.models.User;
import com.example.eksamens_vm.utils.SceneManager;
import javafx.event.ActionEvent;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class UserService {

    private JsonService jsonService = new JsonService();
    private Session session = Session.getInstance();


    public int generateNewId(){
         List<User> users = jsonService.getAll("users.json", User.class);
         return users.isEmpty() ? 1 : users.getLast().getId() + 1;

    }

    public boolean userExists(String username) {
        List<User> users = jsonService.getAll("users.json", User.class);
        System.out.println(users);
        return users.stream().anyMatch(user -> user.getUsername().equals(username));
    }

    public boolean userExists(int userId) {
        List<User> users = jsonService.getAll("users.json", User.class);
        System.out.println(users);
        return users.stream().anyMatch(user -> user.getId() == userId);
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

    public void addUserToGroup(int userId, int groupId) throws UserNotFoundException {
        User user = getUserById(userId);

    }


    public void logout(ActionEvent event) {
        session.setLoggedInUser(null);
        session.setJoinedRoom(null);
        SceneManager.switchScenes(event, "login.fxml", "login");
        SceneManager.clearHistory();
    }
}
