package com.example.eksamens_vm.controllers;

import com.example.eksamens_vm.data.Session;
import com.example.eksamens_vm.exceptions.RoomNotFoundException;
import com.example.eksamens_vm.models.User;
import com.example.eksamens_vm.services.JsonService;
import com.example.eksamens_vm.services.RoomService;
import com.example.eksamens_vm.services.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class RoomOwnerController implements Initializable {

    @FXML
    private ImageView logo;
    @FXML
    private Text text;
    @FXML
    private Label joinCode;
    @FXML
    private ListView<String> requestList;

    private Image image = new Image(Objects.requireNonNull(getClass().getResource("/images/logo.png")).toExternalForm());
    private Session session = Session.getInstance();
    private RoomService roomService = new RoomService();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        logo.setImage(image);
        text.setText(session.getJoinedRoom().getName());
        joinCode.setText("Join Code- " + session.getJoinedRoom().getJoinCode());

        try {
            requestList.getItems().addAll(
              roomService.getAllRequestsNames(session.getJoinedRoom().getId())
            );
        } catch (RoomNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void acceptUser(ActionEvent event) {

    }

    @FXML
    private void rejectUser(ActionEvent event) {

    }
}
