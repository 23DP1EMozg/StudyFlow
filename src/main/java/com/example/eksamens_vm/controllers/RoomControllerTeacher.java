package com.example.eksamens_vm.controllers;

import com.example.eksamens_vm.data.Session;
import com.example.eksamens_vm.exceptions.GroupNotFoundException;
import com.example.eksamens_vm.models.*;
import com.example.eksamens_vm.services.TestService;
import com.example.eksamens_vm.utils.SceneManager;
import com.example.eksamens_vm.utils.TypeConvertionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class RoomControllerTeacher implements Initializable {

    @FXML
    private ImageView logo;
    @FXML
    private Text text;
    @FXML
    private TableView<RecentTestTeacherTable> table;
    @FXML
    private TableColumn<RecentTestTeacherTable, String> nameColumn;
    @FXML
    private TableColumn<RecentTestTeacherTable, String> groupColumn;

    private Image image = new Image(Objects.requireNonNull(getClass().getResource("/images/logo.png")).toExternalForm());
    private Session session = Session.getInstance();
    private TestService testService = new TestService();
    private TypeConvertionManager typeConvertionManager = new TypeConvertionManager();


    @FXML
    private void goBack(ActionEvent event) {
        SceneManager.goBack(event);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        logo.setImage(image);
        text.setText(session.getJoinedRoom().getName());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
        groupColumn.setCellValueFactory(cellData -> cellData.getValue().getGroupProperty());

        try {
            List<TestModel> testModels = testService.getAllTeachersCompletedTestsinRoom(session.getJoinedRoom().getId(), session.getLoggedInUser().getId());
            table.setItems(typeConvertionManager.testsToRecentTestTeacherTable(testModels));

        } catch (GroupNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void toGroups(ActionEvent event) {
        SceneManager.switchScenes(event, "groups", "Groups");
    }

    @FXML
    private void toAllUsers(ActionEvent event) {
        SceneManager.switchScenes(event, "all_users", "All Users");
    }

    @FXML
    private void toTests(ActionEvent event) {
        SceneManager.switchScenes(event, "tests", "tests");
    }
}
