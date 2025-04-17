package com.example.eksamens_vm.controllers;

import com.example.eksamens_vm.exceptions.GroupNotFoundException;
import com.example.eksamens_vm.exceptions.RoomNotFoundException;
import com.example.eksamens_vm.exceptions.UserNotFoundException;
import com.example.eksamens_vm.models.Test;
import com.example.eksamens_vm.models.TestAttemptTable;
import com.example.eksamens_vm.models.User;
import com.example.eksamens_vm.services.GroupService;
import com.example.eksamens_vm.services.TestService;
import com.example.eksamens_vm.services.UserService;
import com.example.eksamens_vm.utils.SceneManager;
import com.example.eksamens_vm.utils.TypeConvertionManager;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.InputMismatchException;
import java.util.List;
import java.util.ResourceBundle;

public class TestViewTeacherController{

    public TestViewTeacherController() {
        //no-args controller
    }
    private TestService testService = new TestService();
    private GroupService groupService = new GroupService();
    private TypeConvertionManager typeConvertionManager = new TypeConvertionManager();
    private UserService userService = new UserService();
    @FXML
    private TextField percentageInput;
    @FXML
    private TableView<TestAttemptTable> table;
    @FXML
    private TableColumn<TestAttemptTable, String> studentColumn;
    @FXML
    private TableColumn<TestAttemptTable, String> gradeColumn;
    @FXML
    private TableColumn<TestAttemptTable, String> percentageColumn;
    @FXML
    private Text error;
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
    private Text text;

    private Test test;

    public void setTest(Test newTest) {
        test = newTest;
        text.setText(test.getName());
        initialize();
    }

    @FXML
    private void save(ActionEvent event) {
        try{
            User user = userService.getUserByUsername(table.getSelectionModel().getSelectedItem().getStudent());
            testService.saveTestAttempt(test.getId(),user.getId(), percentageInput.getText());
            error.setFill(Color.GREENYELLOW);
            error.setText("Saved!");
        }catch (InputMismatchException e){
            error.setText(e.getMessage());
        }
    }

    private void initialize() {
        studentColumn.setCellValueFactory(cellData -> cellData.getValue().getStudentProperty());
        gradeColumn.setCellValueFactory(cellData -> {
            int grade = cellData.getValue().getGrade();
            return new SimpleStringProperty(
                    grade == -1 ? "not graded" : String.valueOf(grade)
            );
        });
        percentageColumn.setCellValueFactory(cellData -> {
            double percentage = cellData.getValue().getPercentage();
            return new SimpleStringProperty(
                    percentage == -1 ? "not graded" : String.valueOf(percentage)
            );
        });


        try {
            List<User> users = groupService.getAllUsersInGroup(test.getGroupId());
            table.setItems(typeConvertionManager.convertUsersToTestAttemptTable(users));
        } catch (GroupNotFoundException e) {
            throw new RuntimeException(e);
        } catch (RoomNotFoundException e) {
            throw new RuntimeException(e);
        } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


}
