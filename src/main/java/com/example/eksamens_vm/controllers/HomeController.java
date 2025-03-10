package com.example.eksamens_vm.controllers;

import com.example.eksamens_vm.data.Session;
import com.example.eksamens_vm.enums.UserRole;
import com.example.eksamens_vm.exceptions.RoomNotFoundException;
import com.example.eksamens_vm.models.Room;
import com.example.eksamens_vm.models.User;
import com.example.eksamens_vm.services.RoomService;
import com.example.eksamens_vm.services.UserService;
import com.example.eksamens_vm.utils.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.net.URL;
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
    @FXML
    private TextField requestCodeInput;


    private Session session = Session.getInstance();
    private Image image = new Image(Objects.requireNonNull(getClass().getResource("/images/logo.png")).toExternalForm());
    private RoomService roomService = new RoomService();
    private UserService userService = new UserService();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        welcome.setText("Welcome " + session.getLoggedInUser().getUsername() + "!");
        logo.setImage(image);
        choiceBox.getItems().addAll(userService.getAllRoomNames(session.getLoggedInUser()));

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
            User user = session.getLoggedInUser();

            String fxmlFile = "";
            if(user.getId() == room.getOwner()){
                fxmlFile = "room_owner.fxml";
            }else if(user.getUserType().equals(UserRole.TEACHER)){
                fxmlFile = "room_teacher.fxml";
            }else{
                fxmlFile = "room_student.fxml";
            }

            SceneManager.switchScenes(event, fxmlFile, room.getName());
        } catch (RoomNotFoundException e) {
            error.setText(e.getMessage());
        }
    }

    @FXML
    private void requestRoom(){
        int joinCode = Integer.parseInt(requestCodeInput.getText());

        try{
            Room room = roomService.requestRoom(joinCode, session.getLoggedInUser());
            error.setFill(Color.GREENYELLOW);
            error.setText("Requested to join room- " + room.getName());

        }catch (RoomNotFoundException e){
            error.setText(e.getMessage());
        }
    }
}
