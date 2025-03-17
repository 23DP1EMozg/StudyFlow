package com.example.eksamens_vm.controllers;

import com.example.eksamens_vm.data.Session;
import com.example.eksamens_vm.exceptions.RoomNotFoundException;
import com.example.eksamens_vm.exceptions.UserNotFoundException;
import com.example.eksamens_vm.models.Room;
import com.example.eksamens_vm.services.RoomService;
import com.example.eksamens_vm.services.UserService;
import com.example.eksamens_vm.utils.SceneManager;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class AllStudentsOwner implements Initializable {
    @FXML
    private ImageView logo;
    @FXML
    private ListView<String> studentList;
    @FXML
    private Text error;

    private RoomService roomService = new RoomService();
    private UserService userService = new UserService();
    private Session session = Session.getInstance();
    private String selectedUser;


    Image image = new Image(Objects.requireNonNull(getClass().getResource("/images/logo.png")).toExternalForm());
    List<String> userNames = new ArrayList<>();

    @FXML
    private void goBack(ActionEvent event) {
        SceneManager.goBack(event);
    }

    @FXML
    private void toEditUser(ActionEvent event) {
        EditUserController editUserController = SceneManager.switchScenesWithController(event, "edit_user.fxml", "Edit User");
        editUserController.hello(session.getLoggedInUser());
    }

    @FXML
    private void removeStudent(ActionEvent event) {
        if(selectedUser == null) {
            error.setText("User not selected!");
        }else{
            int userId = userService.getUserByUsername(selectedUser).getId();
            try {
                roomService.removeUser(userId, session.getJoinedRoom().getId());
                studentList.getItems().remove(selectedUser);
                selectedUser = null;
                error.setText("");
            } catch (UserNotFoundException | RoomNotFoundException e) {
                error.setText(e.getMessage());
            }
        }
    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        logo.setImage(image);
        try {
            userNames = roomService.getAllUsersInRoomNames(session.getJoinedRoom().getId());
            studentList.getItems().addAll(userNames);
        } catch (RoomNotFoundException e) {
            throw new RuntimeException(e);
        } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
        }

        studentList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                selectedUser = studentList.getSelectionModel().getSelectedItem();
            }
        });

    }
}
