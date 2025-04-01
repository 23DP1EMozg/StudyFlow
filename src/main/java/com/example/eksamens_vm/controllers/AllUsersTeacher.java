package com.example.eksamens_vm.controllers;

import com.example.eksamens_vm.data.Session;
import com.example.eksamens_vm.exceptions.GroupNotFoundException;
import com.example.eksamens_vm.exceptions.RoomNotFoundException;
import com.example.eksamens_vm.exceptions.UserNotFoundException;
import com.example.eksamens_vm.services.FilterService;
import com.example.eksamens_vm.services.GroupService;
import com.example.eksamens_vm.services.RoomService;
import com.example.eksamens_vm.utils.SceneManager;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AllUsersTeacher implements Initializable {

    @FXML
    private void goBack(ActionEvent event) {
        SceneManager.goBack(event);
    }
    @FXML
    private ChoiceBox<String> filterBox;
    @FXML
    private ListView<String> studentList;

    private FilterService filterService = new FilterService();
    private RoomService roomService = new RoomService();
    private Session session = Session.getInstance();
    private GroupService groupService = new GroupService();

    @FXML
    private void toGroups(ActionEvent event) {
        SceneManager.switchScenes(event, "groups", "Groups");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            studentList.getItems().addAll(roomService.getAllUsersInRoomNames(session.getJoinedRoom().getId()));
        } catch (RoomNotFoundException e) {
            throw new RuntimeException(e);
        } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
        }

        List<String> allGroupNames = groupService.getAllRoomGroupNames(session.getJoinedRoom().getId());
        allGroupNames.add(0, "All");
        allGroupNames.add(1, "All Teachers");
        allGroupNames.add(2, "All Students");
        filterBox.getItems().addAll(allGroupNames);


        filterBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {

                try {
                    if(filterBox.getValue() == null || filterBox.getValue().equals("All")) {

                        List<String> names = roomService.getAllUsersInRoomNames(session.getJoinedRoom().getId());
                        studentList.getItems().clear();
                        studentList.getItems().addAll(names);

                    }else if(filterBox.getValue().equals("All Teachers")) {
                        List<String> names = filterService.getAllTeachersInRoomNames(session.getJoinedRoom().getId());
                        studentList.getItems().clear();
                        studentList.getItems().addAll(names);
                        System.out.println("teachers");
                    }else if(filterBox.getValue().equals("All Students")) {
                        List<String> names = filterService.getAllStudentsInRoomNames(session.getJoinedRoom().getId());
                        studentList.getItems().clear();
                        studentList.getItems().addAll(names);
                        System.out.println("students");
                    }else{
                        List<String> names = filterService.getAllUsersInGroupNames(filterBox.getValue(), session.getJoinedRoom().getId());
                        studentList.getItems().clear();
                        studentList.getItems().addAll(names);
                    }
                } catch (RoomNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (UserNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (GroupNotFoundException e) {
                    throw new RuntimeException(e);
                }

            }
        });
    }
}
