package com.example.eksamens_vm.controllers;

import com.example.eksamens_vm.data.Session;
import com.example.eksamens_vm.exceptions.UserNotFoundException;
import com.example.eksamens_vm.models.User;
import com.example.eksamens_vm.services.UserService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class HomeController implements Initializable {


    @FXML
    private ImageView logo;

    @FXML
    private Text welcome;
    @FXML
    private ChoiceBox<?> choiceBox;

    Session session = Session.getInstance();
    Image image = new Image(Objects.requireNonNull(getClass().getResource("/images/logo.png")).toExternalForm());



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        welcome.setText("Welcome " + session.getLoggedInUser().getUsername() + "!");
        logo.setImage(image);
    }
}
