package com.example.eksamens_vm.controllers;

import com.example.eksamens_vm.utils.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class GroupsOwnerController implements Initializable {

    private Image image = new Image(Objects.requireNonNull(getClass().getResource("/images/logo.png")).toExternalForm());

    @FXML
    private ImageView logo;


    @FXML
    private void goBack(ActionEvent event) {
        SceneManager.goBack(event);
    }


    @FXML
    private void toAllStudents(ActionEvent event) {
        SceneManager.switchScenes(event, "all_users", "All Users");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        logo.setImage(image);
    }
}
