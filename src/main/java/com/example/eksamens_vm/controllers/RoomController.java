package com.example.eksamens_vm.controllers;

import com.example.eksamens_vm.data.Session;
import com.example.eksamens_vm.exceptions.TestNotFoundException;
import com.example.eksamens_vm.models.RecentTestTable;
import com.example.eksamens_vm.models.Room;
import com.example.eksamens_vm.models.TestAttempt;
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

public class RoomController implements Initializable {

    @FXML
    private ImageView logo;
    @FXML
    private Text text;
    @FXML
    private TableView<RecentTestTable> table;
    @FXML
    private TableColumn<RecentTestTable, String> nameColumn;
    @FXML
    private TableColumn<RecentTestTable, String> gradeColumn;
    @FXML
    private Text avgText;
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
        gradeColumn.setCellValueFactory(cellData -> cellData.getValue().getGradeProperty());

        List<TestAttempt> attempts = testService.getAllUsersCompletedTests(session.getLoggedInUser().getId(), session.getJoinedRoom().getId());

        double avgerageGrade = testService.getUsersAverageGrade(session.getLoggedInUser().getId(), session.getJoinedRoom().getId());
        if(avgerageGrade == -1){
            avgText.setText("You have no average grade");
        }else{
            avgText.setText("Your average grade: " + avgerageGrade);
        }
        try {
            table.setItems(typeConvertionManager.testAttemptsToRecentTestTable(attempts));
        } catch (TestNotFoundException e) {
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
