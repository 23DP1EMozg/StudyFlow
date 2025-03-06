package com.example.eksamens_vm.controllers;

import com.example.eksamens_vm.enums.UserRole;
import com.example.eksamens_vm.exceptions.UserExistsException;
import com.example.eksamens_vm.exceptions.UserFieldEmptyException;
import com.example.eksamens_vm.factory.UserFactory;
import com.example.eksamens_vm.models.User;
import com.example.eksamens_vm.services.JsonService;
import com.example.eksamens_vm.services.RegisterService;
import com.example.eksamens_vm.services.UserService;
import com.example.eksamens_vm.utils.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class RegisterController{

    private static UserRole userRole;
    private RegisterService registerService = new RegisterService();
    private UserService userService = new UserService();

    @FXML
    private TextField passwordAgainField;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField usernameField;

    @FXML
    private Text alert;
    @FXML
    private Button registerButton;



    public static void setUserRole(UserRole role) {
        userRole = role;
    }

    public static UserRole getUserRole() {
        return userRole;
    }

    public void selectTeacher(ActionEvent event) {
        setUserRole(UserRole.TEACHER);
        System.out.println(userRole);
        continueRegister(event);
    }

    public void selectStudent(ActionEvent event){
        setUserRole(UserRole.STUDENT);
        System.out.println(userRole);
        continueRegister(event);
    }
    public void print(){
        System.out.println("User Role: " + getUserRole());
    }
    private void continueRegister(ActionEvent event) {
        try{
            print();
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/eksamens_vm/register_2.fxml"));
            Parent root = fxmlLoader.load();
            stage.setScene(new Scene(root));
            stage.show();

        }catch (IOException e){
            e.printStackTrace();
        }
    }



    public void register(ActionEvent event){
        try{
            print();
            if(!passwordField.getText().equals(passwordAgainField.getText())){
                alert.setText("Passwords do not match!");
                return;
            }
            int id = userService.generateNewId();
            User user = new User(id, usernameField.getText(), passwordField.getText(), getUserRole());
            registerService.register(user);

            SceneManager.switchScenes(event, "/com/example/eksamens_vm/login.fxml", "login");

        }catch (UserExistsException | UserFieldEmptyException e){
            alert.setText(e.getMessage());
        }
    }


    public void toLogin(ActionEvent event) throws IOException {
        SceneManager.switchScenes(event, "/com/example/eksamens_vm/login.fxml", "login");
    }



}
