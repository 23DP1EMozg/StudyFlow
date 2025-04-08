package com.example.eksamens_vm.controllers;

import com.example.eksamens_vm.exceptions.InputFieldEmptyException;
import com.example.eksamens_vm.services.RoomService;
import com.example.eksamens_vm.utils.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class CreateRoomController implements Initializable {


    @FXML
    private Button createButton;

    @FXML
    private ImageView logo;

    @FXML
    private TextField nameInput;
    @FXML
    private Text error;

    RoomService roomService = new RoomService();

    Image image = new Image(Objects.requireNonNull(getClass().getResource("/images/logo.png")).toExternalForm());


    @FXML
    private void createRoom(ActionEvent event) {
        try{
            String name = nameInput.getText();
            roomService.createRoom(name);
            SceneManager.switchScenes(event, "home_teacher.fxml", "Home");
        }catch (InputFieldEmptyException e){
            error.setText(e.getMessage());
        }
    }

    @FXML
    private void goBack(ActionEvent event) {
        SceneManager.goBack(event);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        logo.setImage(image);

    }

    @FXML
    private void toTests(ActionEvent event) {
        SceneManager.switchScenes(event, "tests", "tests");
    }
}
