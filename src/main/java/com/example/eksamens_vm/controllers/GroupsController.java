package com.example.eksamens_vm.controllers;

import com.example.eksamens_vm.data.Session;
import com.example.eksamens_vm.services.GroupService;
import com.example.eksamens_vm.utils.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class GroupsController implements Initializable {

    private Image image = new Image(Objects.requireNonNull(getClass().getResource("/images/logo.png")).toExternalForm());
    private GroupService groupService = new GroupService();
    private Session session = Session.getInstance();

    @FXML
    private ImageView logo;
    @FXML
    private ListView<String> groupsList;


    @FXML
    private void goBack(ActionEvent event) {
        SceneManager.goBack(event);
    }


    @FXML
    private void toAllUsers(ActionEvent event) {
        SceneManager.switchScenes(event, "all_users", "All Users");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<String> groupNames = groupService.getAllRoomGroupNames(session.getJoinedRoom().getId());
        groupsList.getItems().addAll(groupNames);
        logo.setImage(image);
    }
}
