package com.example.eksamens_vm.controllers;

import com.example.eksamens_vm.data.Session;
import com.example.eksamens_vm.exceptions.UserNotFoundException;
import com.example.eksamens_vm.models.User;
import com.example.eksamens_vm.services.UserService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    @FXML
    Text text;

    private UserService userService = new UserService();
    private Session session = Session.getInstance();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
            User user = userService.getUserById(session.getLoggedInUser().getId());
            text.setText("Logged in as: " + user.getUsername());
        }catch (UserNotFoundException e){

        }
    }
}
