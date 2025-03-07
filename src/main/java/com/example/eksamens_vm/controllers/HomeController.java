package com.example.eksamens_vm.controllers;

import com.example.eksamens_vm.data.Session;
import com.example.eksamens_vm.exceptions.RoomNotFoundException;
import com.example.eksamens_vm.exceptions.UserNotFoundException;
import com.example.eksamens_vm.models.Room;
import com.example.eksamens_vm.models.User;
import com.example.eksamens_vm.services.RoomService;
import com.example.eksamens_vm.services.UserService;
import com.example.eksamens_vm.utils.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class HomeController implements Initializable {


    @FXML
    private ImageView logo;

    @FXML
    private Text welcome;
    @FXML
    private ChoiceBox<String> choiceBox;
    @FXML
    private Text error;

    private Session session = Session.getInstance();
    private Image image = new Image(Objects.requireNonNull(getClass().getResource("/images/logo.png")).toExternalForm());
    private RoomService roomService = new RoomService();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        welcome.setText("Welcome " + session.getLoggedInUser().getUsername() + "!");
        logo.setImage(image);
        choiceBox.getItems().addAll(roomService.getAllRoomNames());

    }

    @FXML
    private void toCreateRoom(ActionEvent event) {
        SceneManager.switchScenes(event, "create_room.fxml", "create room");
    }

    @FXML
    private void joinRoom(ActionEvent event) {
        String roomName = choiceBox.getValue();
        try {
            Room room = roomService.getRoomByName(roomName);
            session.setJoinedRoom(room);
            SceneManager.switchScenes(event, "room.fxml", room.getName());
        } catch (RoomNotFoundException e) {
            error.setText(e.getMessage());
        }
    }


}
