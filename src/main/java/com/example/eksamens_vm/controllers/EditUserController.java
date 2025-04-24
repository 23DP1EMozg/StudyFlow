package com.example.eksamens_vm.controllers;

import com.example.eksamens_vm.data.Session;
import com.example.eksamens_vm.exceptions.GroupNotFoundException;
import com.example.eksamens_vm.exceptions.RoomNotFoundException;
import com.example.eksamens_vm.exceptions.UserNotFoundException;
import com.example.eksamens_vm.models.User;
import com.example.eksamens_vm.services.GroupService;
import com.example.eksamens_vm.services.UserService;
import com.example.eksamens_vm.utils.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class EditUserController implements Initializable {

    @FXML
    private ImageView logo;

    @FXML
    private Text studentText;
    @FXML
    private ChoiceBox<String> menu;
    @FXML
    private Text error;


    private User user;
    private GroupService groupService = new GroupService();
    private Session session = Session.getInstance();


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
        SceneManager.switchScenes(event, "groups", "All Groups");
    }

    public void initializeUser(User usr){
        user = usr;
        studentText.setText("Editing student: " + user.getUsername());
    }

    @FXML
    private void saveGroup(ActionEvent event) {
        String groupName = menu.getValue();
        if(groupName == null){
            error.setFill(Color.RED);
            error.setText("Please select a group");
            return;
        }
        try {
            int groupId = groupService.getRoomGroupByName(groupName, session.getJoinedRoom().getId()).getId();
            groupService.addUserToGroup(groupId, user.getId());
            error.setFill(Color.GREENYELLOW);
            error.setText("Successfully added user to group: " + groupName);
        } catch (GroupNotFoundException e) {
            error.setText(e.getMessage());
        } catch (UserNotFoundException e) {
            error.setText(e.getMessage());
        } catch (RoomNotFoundException e) {
            error.setText(e.getMessage());
        }


    }


    Image image = new Image(Objects.requireNonNull(getClass().getResource("/images/logo.png")).toExternalForm());


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        logo.setImage(image);
        List<String> groupNames = groupService.getAllRoomGroupNames(session.getJoinedRoom().getId());
        menu.getItems().addAll(groupNames);


    }

    @FXML
    private void toTests(ActionEvent event) {
        SceneManager.switchScenes(event, "tests", "tests");
    }
}
