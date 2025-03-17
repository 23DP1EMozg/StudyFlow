package com.example.eksamens_vm.controllers;

import com.example.eksamens_vm.models.User;
import com.example.eksamens_vm.services.UserService;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class EditUserController implements Initializable {

    private User user;
    private UserService userService = new UserService();

    public void hello(User user){
        System.out.println(user.getUsername());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
