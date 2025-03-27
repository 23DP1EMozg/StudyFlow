package com.example.eksamens_vm.controllers;

import com.example.eksamens_vm.enums.UserRole;
import com.example.eksamens_vm.exceptions.InvalidCredentialsException;
import com.example.eksamens_vm.exceptions.InputFieldEmptyException;
import com.example.eksamens_vm.exceptions.UserNotFoundException;
import com.example.eksamens_vm.models.User;
import com.example.eksamens_vm.services.JsonService;
import com.example.eksamens_vm.services.LoginService;
import com.example.eksamens_vm.services.UserService;
import com.example.eksamens_vm.utils.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    ImageView logo;

    Image image = new Image(Objects.requireNonNull(getClass().getResource("/images/logo.png")).toExternalForm());
    LoginService loginService = new LoginService();

    @FXML
    private TextField passwordField;

    @FXML
    private TextField usernameField;

    @FXML
    private Text text;


    public void login(ActionEvent event) {
        try{
            User user = loginService.login(usernameField.getText(), passwordField.getText());
            SceneManager.switchScenes(event,
                    "home",
                    "home");
        }catch (UserNotFoundException | InvalidCredentialsException | InputFieldEmptyException e){
            text.setText(e.getMessage());
        }
    }

    public void toRegister(ActionEvent event) throws IOException, URISyntaxException {
        SceneManager.switchScenes(event, "register_1.fxml", "register");
    }

    @FXML
    private void goBack(ActionEvent event) {
        SceneManager.goBack(event);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        logo.setImage(image);
    }
}
