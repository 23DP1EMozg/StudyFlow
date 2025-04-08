package com.example.eksamens_vm.controllers;

import com.example.eksamens_vm.data.Session;
import com.example.eksamens_vm.exceptions.NotFoundException;
import com.example.eksamens_vm.exceptions.RoomNotFoundException;
import com.example.eksamens_vm.exceptions.UserNotFoundException;
import com.example.eksamens_vm.models.User;
import com.example.eksamens_vm.services.JsonService;
import com.example.eksamens_vm.services.RoomService;
import com.example.eksamens_vm.services.UserService;
import com.example.eksamens_vm.utils.SceneManager;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
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
    @FXML
    private ListView<String> requestList;

    private Image image = new Image(Objects.requireNonNull(getClass().getResource("/images/logo.png")).toExternalForm());
    private Session session = Session.getInstance();
    private RoomService roomService = new RoomService();
    private String selectedUserRequest;
    private UserService userService = new UserService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        logo.setImage(image);
        text.setText(session.getJoinedRoom().getName());
        joinCode.setText("Join Code- " + session.getJoinedRoom().getJoinCode());

        try {
            requestList.getItems().addAll(
              roomService.getAllRequestsNames(session.getJoinedRoom().getId())
            );
        } catch (RoomNotFoundException e) {
            throw new RuntimeException(e);
        } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
        }

        requestList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                selectedUserRequest = requestList.getSelectionModel().getSelectedItem();
            }
        });
    }

    @FXML
    private void acceptUser(ActionEvent event) {
        if(selectedUserRequest != null){
            User user = userService.getUserByUsername(selectedUserRequest);
            try {
                roomService.acceptUserRequest(user.getId(), session.getJoinedRoom().getId());
                requestList.getItems().clear();
                requestList.getItems().addAll(
                        roomService.getAllRequestsNames(session.getJoinedRoom().getId())
                );
                selectedUserRequest = null;
            } catch (RoomNotFoundException e) {
                throw new RuntimeException(e);
            } catch (NotFoundException e) {
                throw new RuntimeException(e);
            } catch (UserNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    private void rejectUser(ActionEvent event) {
        if(selectedUserRequest != null){
            User user = userService.getUserByUsername(selectedUserRequest);
            try {
                roomService.rejectUserRequest(user.getId(), session.getJoinedRoom().getId());
                requestList.getItems().clear();
                requestList.getItems().addAll(
                        roomService.getAllRequestsNames(session.getJoinedRoom().getId())
                );
                selectedUserRequest = null;
                System.out.println(roomService.getAllRequestsNames(session.getJoinedRoom().getId()));
            } catch (RoomNotFoundException e) {
                throw new RuntimeException(e);
            } catch (NotFoundException e) {
                throw new RuntimeException(e);
            } catch (UserNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    private void goBack(ActionEvent event) {
        SceneManager.goBack(event);
    }

    @FXML
    private void toAllUsers(ActionEvent event) {

        SceneManager.switchScenes(event, "all_users", "All Users");
    }

    @FXML
    private void toGroups(ActionEvent event) {
        SceneManager.switchScenes(event, "groups", "Groups");
    }

    @FXML
    private void toTests(ActionEvent event) {
        SceneManager.switchScenes(event, "tests", "tests");
    }
}
