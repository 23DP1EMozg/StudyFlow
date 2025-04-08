package com.example.eksamens_vm.controllers;

import com.example.eksamens_vm.data.Session;
import com.example.eksamens_vm.exceptions.GroupExistsException;
import com.example.eksamens_vm.exceptions.GroupNotFoundException;
import com.example.eksamens_vm.exceptions.RoomNotFoundException;
import com.example.eksamens_vm.models.Group;
import com.example.eksamens_vm.models.GroupTable;
import com.example.eksamens_vm.models.UserTable;
import com.example.eksamens_vm.services.GroupService;
import com.example.eksamens_vm.utils.SceneManager;
import com.example.eksamens_vm.utils.TypeConvertionManager;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
    private TableView<GroupTable> table;
    @FXML
    private TableColumn<GroupTable, String> nameColumn;
    @FXML
    private TableColumn<GroupTable, String> countColumn;

    private Session session = Session.getInstance();
    private TypeConvertionManager typeConvertionManager = new TypeConvertionManager();


    @FXML
    private void goBack(ActionEvent event) {
        SceneManager.goBack(event);
    }


    @FXML
    private void toAllUsers(ActionEvent event) {
        SceneManager.switchScenes(event, "all_users", "All Users");
    }

    @FXML
    private void createGroup(ActionEvent event) {
        String groupName = groupNameInput.getText();

        try{
            groupService.createGroup(groupName, session.getJoinedRoom().getId());
            error.setFill(Color.LIMEGREEN);
            error.setText("Created group " + groupName);

            List<Group> groups = groupService.getAllGroupsInRoom(session.getJoinedRoom().getId());
            table.getItems().clear();
            table.setItems(typeConvertionManager.convertToGroupTable(groups));
        }catch(GroupExistsException e){
            error.setFill(Color.RED);
            error.setText(e.getMessage());
        } catch (RoomNotFoundException e) {
            throw new RuntimeException(e);
        } catch (GroupNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Group> groups = groupService.getAllGroupsInRoom(session.getJoinedRoom().getId());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
        countColumn.setCellValueFactory(cellData -> cellData.getValue().getUsersCountProperty());

        try {
            ObservableList<GroupTable> groupTables = typeConvertionManager.convertToGroupTable(groups);
            table.setItems(groupTables);
        } catch (GroupNotFoundException e) {
            throw new RuntimeException(e);
        } catch (RoomNotFoundException e) {
            throw new RuntimeException(e);
        }
        logo.setImage(image);
    }

    @FXML
    private void toTests(ActionEvent event) {
        SceneManager.switchScenes(event, "tests", "tests");
    }
}
