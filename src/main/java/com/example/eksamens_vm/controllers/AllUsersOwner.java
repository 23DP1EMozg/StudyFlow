package com.example.eksamens_vm.controllers;

import com.example.eksamens_vm.data.Session;
import com.example.eksamens_vm.exceptions.GroupNotFoundException;
import com.example.eksamens_vm.exceptions.NotFoundException;
import com.example.eksamens_vm.exceptions.RoomNotFoundException;
import com.example.eksamens_vm.exceptions.UserNotFoundException;
import com.example.eksamens_vm.models.User;
import com.example.eksamens_vm.models.UserTable;
import com.example.eksamens_vm.services.FilterService;
import com.example.eksamens_vm.services.GroupService;
import com.example.eksamens_vm.services.RoomService;
import com.example.eksamens_vm.services.UserService;
import com.example.eksamens_vm.utils.SceneManager;
import com.example.eksamens_vm.utils.TypeConvertionManager;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.*;

public class AllUsersOwner implements Initializable {
    @FXML
    private ImageView logo;
    @FXML
    private TableView<UserTable> table;
    @FXML
    private TableColumn<UserTable, String> usernameColumn;
    @FXML
    private TableColumn<UserTable, String> groupColumn;
    @FXML
    private TableColumn<UserTable, String> roleColumn;
    @FXML
    private Text error;
    @FXML
    private ChoiceBox<String> filterBox;


    private List<String> filters = new ArrayList<>();

    private RoomService roomService = new RoomService();
    private UserService userService = new UserService();
    private GroupService groupService = new GroupService();
    private FilterService filterService = new FilterService();
    private TypeConvertionManager typeConvertionManager = new TypeConvertionManager();
    private Session session = Session.getInstance();
    private UserTable selectedUser;


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
            User user = userService.getUserByUsername(selectedUser.getUsername());
            assert editUserController != null;
            editUserController.initializeUser(user);
        }

    }

    @FXML
    private void removeStudent(ActionEvent event) {
        if(selectedUser == null) {
            error.setText("User not selected!");
        }else{
            int userId = userService.getUserByUsername(selectedUser.getUsername()).getId();
            try {
                roomService.removeUser(userId, session.getJoinedRoom().getId());
                table.getItems().remove(selectedUser);
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
            List<User> users = roomService.getAllUsersInRoom(session.getJoinedRoom().getId());
            usernameColumn.setCellValueFactory(cellData -> cellData.getValue().getUsernameProperty());
            groupColumn.setCellValueFactory(cellData -> cellData.getValue().getGroupProperty());
            roleColumn.setCellValueFactory(cellData -> cellData.getValue().getRoleProperty());

            ObservableList<UserTable> userTableList = typeConvertionManager.convertToUserTable(users, session.getJoinedRoom().getId());
            table.setItems(userTableList);
        } catch (RoomNotFoundException e) {
            throw new RuntimeException(e);
        } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
        } catch (GroupNotFoundException e) {
            throw new RuntimeException(e);
        }


        table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<UserTable>() {

            @Override
            public void changed(ObservableValue<? extends UserTable> observableValue, UserTable userTable, UserTable t1) {
                selectedUser = table.getSelectionModel().getSelectedItem();
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

                            List<User> usersInRoom = roomService.getAllUsersInRoom(session.getJoinedRoom().getId());
                            table.getItems().clear();
                            table.getItems().addAll(typeConvertionManager.convertToUserTable(usersInRoom, session.getJoinedRoom().getId()));

                        }else if(filterBox.getValue().equals("All Teachers")) {
                            List<User> users = filterService.getAllTeachersInRoom(session.getJoinedRoom().getId());
                            table.getItems().clear();
                            table.setItems(typeConvertionManager.convertToUserTable(users, session.getJoinedRoom().getId()));
                            System.out.println("teachers");
                        }else if(filterBox.getValue().equals("All Students")) {
                            List<User> users = filterService.getAllStudentsInRoom(session.getJoinedRoom().getId());
                            table.getItems().clear();
                            table.getItems().addAll(typeConvertionManager.convertToUserTable(users, session.getJoinedRoom().getId()));
                            System.out.println("students");
                        }else{
                            List<User> users = filterService.getAllUsersInGroup(filterBox.getValue(), session.getJoinedRoom().getId());
                            table.getItems().clear();
                            table.getItems().addAll(typeConvertionManager.convertToUserTable(users, session.getJoinedRoom().getId()));
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
