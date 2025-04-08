package com.example.eksamens_vm.controllers;

import com.example.eksamens_vm.utils.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class TestsController implements Initializable {
    @FXML
    private ImageView logo;

    private Image image = new Image(Objects.requireNonNull(getClass().getResource("/images/logo.png")).toExternalForm());

    @FXML
    private void toTests(ActionEvent event) {
        SceneManager.switchScenes(event, "tests", "tests");
    }

    @FXML
    private void goBack(ActionEvent event) {
        SceneManager.goBack(event);
    }

    @FXML
    private void toAllUsers(ActionEvent event) {
        SceneManager.switchScenes(event, "all_users", "all users");
    }

    @FXML
    private void toGroups(ActionEvent event) {
        SceneManager.switchScenes(event, "groups", "groups");
    }

    @FXML
    private void toCreateTest(ActionEvent event) {
        SceneManager.switchScenes(event, "create_test.fxml", "create test");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        logo.setImage(image);
    }

}
