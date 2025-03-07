package com.example.eksamens_vm.services;

import com.example.eksamens_vm.data.Session;
import com.example.eksamens_vm.exceptions.UserExistsException;
import com.example.eksamens_vm.exceptions.InputFieldEmptyException;
import com.example.eksamens_vm.factory.UserFactory;
import com.example.eksamens_vm.models.User;

public class RegisterService {

    JsonService jsonService = new JsonService();
    UserService userService = new UserService();
    Session session = Session.getInstance();

    public void register(User user) throws UserExistsException, InputFieldEmptyException {

        if(userService.userExists(user.getUsername())) {
            throw new UserExistsException("user already exists!");
        }

        if(
            user.getUsername().isBlank() ||
            user.getPassword().isBlank()
        ){
            throw new InputFieldEmptyException("you must fill all fields!");
        }

        User savedUser = UserFactory.createUser(user);

        jsonService.save(savedUser, "users.json", User.class);
        session.setLoggedInUser(user);
        System.out.println("saved user: " + user.getUsername());
    }
}
