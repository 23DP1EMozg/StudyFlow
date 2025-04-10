package com.example.eksamens_vm.controllers;

import com.example.eksamens_vm.data.Session;
import com.example.eksamens_vm.exceptions.GroupNotFoundException;
import com.example.eksamens_vm.exceptions.UserNotFoundException;
import com.example.eksamens_vm.models.Test;
import com.example.eksamens_vm.models.TestTable;
import com.example.eksamens_vm.services.TestService;
import com.example.eksamens_vm.utils.SceneManager;
import com.example.eksamens_vm.utils.TypeConvertionManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class TestsController implements Initializable {
    @FXML
    private ImageView logo;
    @FXML
    private TableView<TestTable> table;
    @FXML
    private TableColumn<TestTable, String> nameColumn;
    @FXML
    private TableColumn<TestTable, String> groupColumn;
    @FXML
    private TableColumn<TestTable, String> teacherColumn;
    @FXML
    private TableColumn<TestTable, String> statusColumn;

    private Image image = new Image(Objects.requireNonNull(getClass().getResource("/images/logo.png")).toExternalForm());
    private TypeConvertionManager typeConvertionManager = new TypeConvertionManager();
    private Session session = Session.getInstance();
    private TestService testService = new TestService();

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
    private void toCreateTest(ActionEvent event) {
        SceneManager.switchScenes(event, "create_test.fxml", "create test");
    }





    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        logo.setImage(image);

        nameColumn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
        groupColumn.setCellValueFactory(cellData -> cellData.getValue().getGroupProperty());
        teacherColumn.setCellValueFactory(cellData -> cellData.getValue().getTeacherProperty());
        statusColumn.setCellValueFactory(cellData -> cellData.getValue().getStatusProperty());

        List<Test> userTests = testService.getAllUserCreatedTests(session.getLoggedInUser().getId());
        try {
            ObservableList<TestTable> testTable = typeConvertionManager.convertToTestTable(userTests);
            table.setItems(testTable);
        } catch (GroupNotFoundException | UserNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
