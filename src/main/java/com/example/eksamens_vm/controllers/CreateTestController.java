package com.example.eksamens_vm.controllers;

import com.example.eksamens_vm.data.Session;
import com.example.eksamens_vm.exceptions.GroupNotFoundException;
import com.example.eksamens_vm.exceptions.InputFieldEmptyException;
import com.example.eksamens_vm.services.GroupService;
import com.example.eksamens_vm.services.TestService;
import com.example.eksamens_vm.utils.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class CreateTestController implements Initializable {

    @FXML
    private ImageView logo;
    @FXML
    private TextField nameInput;
    @FXML
    private ChoiceBox<String> groupSelect;
    @FXML
    private Text error;

    private Image image = new Image(Objects.requireNonNull(getClass().getResource("/images/logo.png")).toExternalForm());
    private TestService testService = new TestService();
    private GroupService groupService = new GroupService();
    private Session session = Session.getInstance();
    @FXML
    private void toTests(ActionEvent event) {
        SceneManager.switchScenes(event, "tests", "tests");
    }

    @FXML
    private void goBack(ActionEvent event) {
        SceneManager.goBack(event);
    }

    @FXML
    private void toAllUsers(ActionEvent event) {
        SceneManager.switchScenes(event, "all_users", "all users");
    }

    @FXML
    private void toGroups(ActionEvent event) {
        SceneManager.switchScenes(event, "groups", "groups");
    }

    @FXML
    private void createTest(ActionEvent event) {
        String name = nameInput.getText();
        String groupName = groupSelect.getValue();

        try {
            testService.createTest(name, groupName);
            error.setFill(Color.GREENYELLOW);
            error.setText("Test created!");
        } catch (GroupNotFoundException | InputFieldEmptyException e) {
            error.setText(e.getMessage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        logo.setImage(image);
        List<String> groupNames = groupService.getAllRoomGroupNames(session.getJoinedRoom().getId());
        groupSelect.getItems().addAll(groupNames);
    }
}
