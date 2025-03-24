package com.example.eksamens_vm.controllers;

import com.example.eksamens_vm.data.Session;
import com.example.eksamens_vm.exceptions.GroupExistsException;
import com.example.eksamens_vm.services.GroupService;
import com.example.eksamens_vm.utils.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class GroupsOwnerController implements Initializable {

    private Image image = new Image(Objects.requireNonNull(getClass().getResource("/images/logo.png")).toExternalForm());
    private GroupService groupService = new GroupService();

    @FXML
    private ImageView logo;
    @FXML
    private TextField groupNameInput;
    @FXML
    private Text error;
    @FXML
    private ListView<String> groupsList;

    private Session session = Session.getInstance();


    @FXML
    private void goBack(ActionEvent event) {
        SceneManager.goBack(event);
    }


    @FXML
    private void toAllStudents(ActionEvent event) {
        SceneManager.switchScenes(event, "all_users", "All Users");
    }

    @FXML
    private void createGroup(ActionEvent event) {
        String groupName = groupNameInput.getText();

        try{
            groupService.createGroup(groupName, session.getJoinedRoom().getId());
            error.setFill(Color.LIMEGREEN);
            error.setText("Created group " + groupName);

            List<String> groupNames = groupService.getAllRoomGroupNames(session.getJoinedRoom().getId());
            groupsList.getItems().clear();
            groupsList.getItems().addAll(groupNames);
        }catch(GroupExistsException e){
            error.setFill(Color.RED);
            error.setText(e.getMessage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<String> groupNames = groupService.getAllRoomGroupNames(session.getJoinedRoom().getId());
        groupsList.getItems().addAll(groupNames);
        logo.setImage(image);
    }
}
