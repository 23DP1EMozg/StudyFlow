package com.example.eksamens_vm.controllers;

import com.example.eksamens_vm.data.Session;
import com.example.eksamens_vm.exceptions.GroupNotFoundException;
import com.example.eksamens_vm.exceptions.RoomNotFoundException;
import com.example.eksamens_vm.models.Group;
import com.example.eksamens_vm.models.GroupTable;
import com.example.eksamens_vm.services.GroupService;
import com.example.eksamens_vm.utils.SceneManager;
import com.example.eksamens_vm.utils.TypeConvertionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class GroupsController implements Initializable {

    private Image image = new Image(Objects.requireNonNull(getClass().getResource("/images/logo.png")).toExternalForm());
    private GroupService groupService = new GroupService();
    private Session session = Session.getInstance();
    private TypeConvertionManager typeConvertionManager = new TypeConvertionManager();

    @FXML
    private ImageView logo;
    @FXML
    private TableView<GroupTable> table;
    @FXML
    private TableColumn<GroupTable, String> nameColumn;
    @FXML
    private TableColumn<GroupTable, String> countColumn;

    @FXML
    private void goBack(ActionEvent event) {
        SceneManager.goBack(event);
    }


    @FXML
    private void toAllUsers(ActionEvent event) {
        SceneManager.switchScenes(event, "all_users", "All Users");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
        countColumn.setCellValueFactory(cellData -> cellData.getValue().getUsersCountProperty());
        List<Group> groups = groupService.getAllGroupsInRoom(session.getJoinedRoom().getId());
        try {
            table.setItems(typeConvertionManager.convertToGroupTable(groups));
        } catch (GroupNotFoundException e) {
            throw new RuntimeException(e);
        } catch (RoomNotFoundException e) {
            throw new RuntimeException(e);
        }
        logo.setImage(image);
    }
}
