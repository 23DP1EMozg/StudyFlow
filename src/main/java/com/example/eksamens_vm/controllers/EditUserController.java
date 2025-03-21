package com.example.eksamens_vm.controllers;

import com.example.eksamens_vm.models.User;
import com.example.eksamens_vm.services.UserService;
import com.example.eksamens_vm.utils.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class EditUserController implements Initializable {

    @FXML
    private ImageView logo;

    @FXML
    private Text studentText;


    private User user;
    private UserService userService = new UserService();


    @FXML
    private void goBack(ActionEvent event) {
        SceneManager.goBack(event);
    }

    @FXML
    private void toAllStudents(ActionEvent event) {
        SceneManager.switchScenes(event, "all_students_owner.fxml", "All Students");
    }

    public void initializeUser(User usr){
        user = usr;
        studentText.setText("Editing student: " + user.getUsername());
    }
    Image image = new Image(Objects.requireNonNull(getClass().getResource("/images/logo.png")).toExternalForm());


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        logo.setImage(image);
    }
}
