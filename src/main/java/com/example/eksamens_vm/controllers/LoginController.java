package com.example.eksamens_vm.controllers;

import com.example.eksamens_vm.enums.UserRole;
import com.example.eksamens_vm.exceptions.InvalidCredentialsException;
import com.example.eksamens_vm.exceptions.UserFieldEmptyException;
import com.example.eksamens_vm.exceptions.UserNotFoundException;
import com.example.eksamens_vm.models.User;
import com.example.eksamens_vm.services.JsonService;
import com.example.eksamens_vm.services.LoginService;
import com.example.eksamens_vm.services.UserService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    ImageView logo;

    Image image = new Image(Objects.requireNonNull(getClass().getResource("/images/logo.png")).toExternalForm());
    JsonService jsonService = new JsonService();
    UserService userService = new UserService();
    LoginService loginService = new LoginService();

    @FXML
    private TextField passwordField;

    @FXML
    private TextField usernameField;

    @FXML
    private Text text;


    public void login(ActionEvent event) {
        try{
            loginService.login(usernameField.getText(), passwordField.getText());

            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/eksamens_vm/home.fxml"));

            Parent root = loader.load();
            Scene scene = new Scene(root, 1024, 768);
            stage.setTitle("register");
            stage.setScene(scene);
            stage.show();
        }catch (UserNotFoundException | InvalidCredentialsException |UserFieldEmptyException e){
            text.setText(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void toRegister(ActionEvent event) throws IOException, URISyntaxException {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/eksamens_vm/register_1.fxml"));

        Parent root = loader.load();
        Scene scene = new Scene(root, 1024, 768);
        stage.setTitle("register");
        stage.setScene(scene);
        stage.show();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        logo.setImage(image);
    }
}
