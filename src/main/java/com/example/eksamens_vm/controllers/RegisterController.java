package com.example.eksamens_vm.controllers;

import com.example.eksamens_vm.enums.UserRole;
import com.example.eksamens_vm.exceptions.UserExistsException;
import com.example.eksamens_vm.exceptions.InputFieldEmptyException;
import com.example.eksamens_vm.models.User;
import com.example.eksamens_vm.services.RegisterService;
import com.example.eksamens_vm.services.UserService;
import com.example.eksamens_vm.utils.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.List;

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
        SceneManager.switchScenes(event, "register_2.fxml", "create room");
    }



    public void register(ActionEvent event){
        try{
            print();
            if(!passwordField.getText().equals(passwordAgainField.getText())){
                alert.setText("Passwords do not match!");
                return;
            }
            int id = userService.generateNewId();
            User user = new User(id, usernameField.getText(), passwordField.getText(), getUserRole(), List.of());
            registerService.register(user);

            SceneManager.switchScenes(event, "login.fxml", "login");

        }catch (UserExistsException | InputFieldEmptyException e){
            alert.setText(e.getMessage());
        }
    }


    public void toLogin(ActionEvent event) throws IOException {
        SceneManager.switchScenes(event, "login.fxml", "login");
    }
    @FXML
    private void goBack(ActionEvent event) {
        SceneManager.goBack(event);
    }


}
