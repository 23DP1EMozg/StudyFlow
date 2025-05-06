package com.example.eksamens_vm.controllers;

import com.example.eksamens_vm.data.Session;
import com.example.eksamens_vm.enums.TestStatus;
import com.example.eksamens_vm.exceptions.TestNotFoundException;
import com.example.eksamens_vm.exceptions.UserNotFoundException;
import com.example.eksamens_vm.models.TestModel;
import com.example.eksamens_vm.models.TestAttempt;
import com.example.eksamens_vm.services.TestService;
import com.example.eksamens_vm.utils.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class TestViewStudentController {
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
    @FXML
    private Text notGradedText;
    @FXML
    private Pane mainPane;
    @FXML
    private Text gradeText;
    @FXML
    private Text percentageText;
    @FXML
    private Text placementText;
    @FXML
    private Text avgText;
    private TestService testService = new TestService();
    private Session session = Session.getInstance();

    TestModel testModel;

    public void setTest(TestModel testModel) {
        this.testModel = testModel;
        text.setText(testModel.getName());
        initialize();
    }

    private void initialize() {
        if(testModel.getTestStatus() == TestStatus.INCOMPLETE){
            notGradedText.setText("Tests is incomplete!");
            mainPane.setVisible(false);
        }else{
            mainPane.setVisible(true);
            avgText.setText("Average grade in test: " + testService.getAverageGrade(testModel.getId()));
            try {
                TestAttempt attempt = testService.getTestAttempt(testModel.getId(), session.getLoggedInUser().getId());
                gradeText.setText(String.valueOf(attempt.getGrade()));
                percentageText.setText(String.valueOf(attempt.getPercent()));
                int placement = testService.getUsersPlacementInTest(testModel.getId(), session.getLoggedInUser().getId());
                int userCount = testService.getUserCountInTest(testModel.getId());
                placementText.setText("You came in #" + placement + " out of " + userCount + " students!");
            } catch (TestNotFoundException e) {
                throw new RuntimeException(e);
            } catch (UserNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
