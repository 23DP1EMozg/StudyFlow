package com.example.eksamens_vm.controllers;

import com.example.eksamens_vm.data.Session;
import com.example.eksamens_vm.exceptions.GroupNotFoundException;
import com.example.eksamens_vm.exceptions.NotFoundException;
import com.example.eksamens_vm.exceptions.RoomNotFoundException;
import com.example.eksamens_vm.exceptions.UserNotFoundException;
import com.example.eksamens_vm.models.User;
import com.example.eksamens_vm.services.FilterService;
import com.example.eksamens_vm.services.GroupService;
import com.example.eksamens_vm.services.RoomService;
import com.example.eksamens_vm.services.UserService;
import com.example.eksamens_vm.utils.SceneManager;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.*;

public class AllUsersOwner implements Initializable {
    @FXML
    private ImageView logo;
    @FXML
    private ListView<String> studentList;
    @FXML
    private Text error;
    @FXML
    private ChoiceBox<String> filterBox;


    private List<String> filters = new ArrayList<>();

    private RoomService roomService = new RoomService();
    private UserService userService = new UserService();
    private GroupService groupService = new GroupService();
    private FilterService filterService = new FilterService();
    private Session session = Session.getInstance();
    private String selectedUser;


    Image image = new Image(Objects.requireNonNull(getClass().getResource("/images/logo.png")).toExternalForm());

    @FXML
    private void goBack(ActionEvent event) {
        SceneManager.goBack(event);
    }

    @FXML
    private void toEditUser(ActionEvent event) {

        if(selectedUser == null){
            error.setText("User not selected!");
        }else{
            EditUserController editUserController = SceneManager.switchScenesWithController(event, "edit_user.fxml", "Edit User");
            User user = userService.getUserByUsername(selectedUser);
            assert editUserController != null;
            editUserController.initializeUser(user);
        }

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
            } catch (NotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }


    @FXML
    private void toGroups(ActionEvent event) {
        SceneManager.switchScenes(event, "groups", "Groups");
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        logo.setImage(image);
        try {
            studentList.getItems().addAll(roomService.getAllUsersInRoomNames(session.getJoinedRoom().getId()));
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

        List<String> allGroupNames = groupService.getAllRoomGroupNames(session.getJoinedRoom().getId());
        allGroupNames.add(0, "All");
        allGroupNames.add(1, "All Teachers");
        allGroupNames.add(2, "All Students");
        filterBox.getItems().addAll(allGroupNames);


        filterBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {

                    try {
                        if(filterBox.getValue() == null || filterBox.getValue().equals("All")) {

                            List<String> names = roomService.getAllUsersInRoomNames(session.getJoinedRoom().getId());
                            studentList.getItems().clear();
                            studentList.getItems().addAll(names);

                        }else if(filterBox.getValue().equals("All Teachers")) {
                            List<String> names = filterService.getAllTeachersInRoomNames(session.getJoinedRoom().getId());
                            studentList.getItems().clear();
                            studentList.getItems().addAll(names);
                            System.out.println("teachers");
                        }else if(filterBox.getValue().equals("All Students")) {
                            List<String> names = filterService.getAllStudentsInRoomNames(session.getJoinedRoom().getId());
                            studentList.getItems().clear();
                            studentList.getItems().addAll(names);
                            System.out.println("students");
                        }else{
                            List<String> names = filterService.getAllUsersInGroupNames(filterBox.getValue(), session.getJoinedRoom().getId());
                            studentList.getItems().clear();
                            studentList.getItems().addAll(names);
                        }
                    } catch (RoomNotFoundException e) {
                        throw new RuntimeException(e);
                    } catch (UserNotFoundException e) {
                        throw new RuntimeException(e);
                    } catch (GroupNotFoundException e) {
                        throw new RuntimeException(e);
                    }

            }
        });

    }
}
