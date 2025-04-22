package com.example.eksamens_vm.controllers;

import com.example.eksamens_vm.data.Session;
import com.example.eksamens_vm.enums.TestStatus;
import com.example.eksamens_vm.exceptions.TestNotFoundException;
import com.example.eksamens_vm.exceptions.UserNotFoundException;
import com.example.eksamens_vm.models.Test;
import com.example.eksamens_vm.models.TestAttempt;
import com.example.eksamens_vm.services.TestService;
import com.example.eksamens_vm.utils.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.util.List;

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
    private TestService testService = new TestService();
    private Session session = Session.getInstance();

    Test test;

    public void setTest(Test test) {
        this.test = test;
        text.setText(test.getName());
        initialize();
    }

    private void initialize() {
        if(test.getTestStatus() == TestStatus.INCOMPLETE){
            notGradedText.setText("Tests is incomplete!");
            mainPane.setVisible(false);
        }else{
            try {
                TestAttempt attempt = testService.getTestAttempt(test.getId(), session.getLoggedInUser().getId());
                gradeText.setText(String.valueOf(attempt.getGrade()));
                percentageText.setText(String.valueOf(attempt.getPercent()));
                int placement = testService.getUsersPlacementInTest(test.getId(), session.getLoggedInUser().getId());
                int userCount = testService.getUserCountInTest(test.getId());
                placementText.setText("You came in #" + placement + " out of " + userCount + " students!");
            } catch (TestNotFoundException e) {
                throw new RuntimeException(e);
            } catch (UserNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
