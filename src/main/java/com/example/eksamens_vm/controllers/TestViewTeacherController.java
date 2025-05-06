package com.example.eksamens_vm.controllers;

import com.example.eksamens_vm.enums.TestStatus;
import com.example.eksamens_vm.exceptions.GroupNotFoundException;
import com.example.eksamens_vm.exceptions.RoomNotFoundException;
import com.example.eksamens_vm.exceptions.TestNotFoundException;
import com.example.eksamens_vm.exceptions.UserNotFoundException;
import com.example.eksamens_vm.models.TestModel;
import com.example.eksamens_vm.models.TestAttempt;
import com.example.eksamens_vm.models.TestAttemptTable;
import com.example.eksamens_vm.models.User;
import com.example.eksamens_vm.services.GroupService;
import com.example.eksamens_vm.services.TestService;
import com.example.eksamens_vm.services.UserService;
import com.example.eksamens_vm.utils.SceneManager;
import com.example.eksamens_vm.utils.TypeConvertionManager;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.InputMismatchException;
import java.util.List;

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
    private Text sortedText;
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
    @FXML Text avgText;

    private TestModel testModel;

    public void setTest(TestModel newTestModel) {
        testModel = newTestModel;
        text.setText(testModel.getName());
        initialize();
    }

    @FXML
    private void save(ActionEvent event) {

        if(table.getSelectionModel().getSelectedItem() == null) {
            error.setText("please select a student");
            return;
        }



        try{
            User user = userService.getUserByUsername(table.getSelectionModel().getSelectedItem().getStudent());
            if(table.getSelectionModel().getSelectedItem().getGrade() != -1) {
                System.out.println("UPDATE");
                testService.updateTestAttempt(user.getId(), testModel.getId(), Double.parseDouble(percentageInput.getText()));
            }else{
                System.out.println("CREATE");
                testService.saveTestAttempt(testModel.getId(),user.getId(), percentageInput.getText());
            }

            error.setFill(Color.GREENYELLOW);
            error.setText("Saved!");

            List<User> users = groupService.getAllUsersInGroup(testModel.getGroupId());
            List<TestAttempt> testAttempts = testService.mergeTestAttemptsWithUsers(testModel.getId(), users);
            table.getItems().clear();
            testModel = testService.checkAndUpdateTestStatus(testModel.getId());
            if(testModel.getTestStatus() == TestStatus.COMPLETE){
                avgText.setText("Average grade: " + testService.getAverageGrade(testModel.getId()));
                table.setItems(typeConvertionManager.sortTestAttempts(testAttempts));
                sortedText.setText("Sorted by percentage");
            }else{
                table.setItems(typeConvertionManager.convertToTestAttemptTable(testAttempts));
            }
        }catch (InputMismatchException e){
            error.setText(e.getMessage());
        } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
        } catch (RoomNotFoundException e) {
            throw new RuntimeException(e);
        } catch (GroupNotFoundException e) {
            throw new RuntimeException(e);
        } catch (TestNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void initialize() {
        if(testModel.getTestStatus() == TestStatus.COMPLETE){
            avgText.setText("Average grade: " + testService.getAverageGrade(testModel.getId()));
        }
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
            List<User> users = groupService.getAllUsersInGroup(testModel.getGroupId());
            List<TestAttempt> testAttempts = testService.mergeTestAttemptsWithUsers(testModel.getId(), users);
            if(testModel.getTestStatus() == TestStatus.COMPLETE){
                table.setItems(typeConvertionManager.sortTestAttempts(testAttempts));
                sortedText.setText("Sorted by percentage");
            }else{
                table.setItems(typeConvertionManager.convertToTestAttemptTable(testAttempts));
            }

        } catch (GroupNotFoundException e) {
            throw new RuntimeException(e);
        } catch (RoomNotFoundException e) {
            throw new RuntimeException(e);
        } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
        } catch (TestNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


}
