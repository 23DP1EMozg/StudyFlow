package com.example.eksamens_vm.controllers;

import com.example.eksamens_vm.data.Session;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
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

    private Image image = new Image(Objects.requireNonNull(getClass().getResource("/images/logo.png")).toExternalForm());
    private Session session = Session.getInstance();



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        logo.setImage(image);
        text.setText(session.getJoinedRoom().getName());
        joinCode.setText("Join Code- " + session.getJoinedRoom().getJoinCode());
    }
}
