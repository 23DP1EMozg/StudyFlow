package com.example.eksamens_vm.utils;

import com.example.eksamens_vm.data.Session;
import com.example.eksamens_vm.enums.UserRole;
import com.example.eksamens_vm.models.SceneHistory;
import com.example.eksamens_vm.models.User;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SceneManager {

    private static List<SceneHistory> history = new ArrayList<>();
    private static Session session = Session.getInstance();

    public SceneManager() {
        history.add(new SceneHistory("login.fxml", "login"));
    }

    public static void switchScenes(ActionEvent event, String fxmlFile, String title) {
        try {
            String permittedFxmlFile = getPermittedScene(fxmlFile);

            FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource("/com/example/eksamens_vm/" + permittedFxmlFile));
            Parent root = loader.load();
            Scene scene = new Scene(root, 1024, 768);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle(title);
            stage.setResizable(false);
            stage.show();


            if(history.isEmpty() || !history.getLast().getFxmlFile().equals(fxmlFile)) {
                history.add(new SceneHistory(fxmlFile, title));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void switchScenes(Stage stage, String fxmlFile, String title) {
        try {
            String permittedFxmlFile = getPermittedScene(fxmlFile);

            FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource("/com/example/eksamens_vm/" + permittedFxmlFile));
            Parent root = loader.load();
            Scene scene = new Scene(root, 1024, 768);

            stage.setScene(scene);
            stage.setTitle(title);
            stage.setResizable(false);
            stage.show();


            if(history.isEmpty() || !history.getLast().getFxmlFile().equals(fxmlFile)) {
                history.add(new SceneHistory(fxmlFile, title));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static <T> T switchScenesWithController(ActionEvent event, String fxmlFile, String title) {
        try {

            String permittedFxmlFile = getPermittedScene(fxmlFile);

            FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource("/com/example/eksamens_vm/" + permittedFxmlFile));
            Parent root = loader.load();
            Scene scene = new Scene(root, 1024, 768);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle(title);
            stage.setResizable(false);
            stage.show();


            if(history.isEmpty() || !history.getLast().getFxmlFile().equals(fxmlFile)) {
                history.add(new SceneHistory(fxmlFile, title));
            }

            return loader.getController();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }



    public static void goBack(ActionEvent event) {
        if (history.size() >= 2) {
            history.removeLast();
            SceneHistory sceneHistory = history.getLast();
            switchScenes(event, sceneHistory.getFxmlFile(), sceneHistory.getTitle());
        } else {
            System.out.println("no history!");
        }
    }


    public static void clearHistory(){
        history.clear();
    }


    private static String getPermittedScene(String fxmlFile){
        User user = session.getLoggedInUser();
        if(user == null){
            return fxmlFile;
        }


       //ALL USERS PAGE
         if(fxmlFile.equals("all_users")){

            if(user.getId() == session.getJoinedRoom().getOwner()){
                return "all_users_owner.fxml";
            }

            switch(user.getUserType()){
                case STUDENT -> {
                    return "all_users_student.fxml";
                }

                case TEACHER -> {
                    return "all_users_teacher.fxml";
                }
            }
        }


         //HOME PAGE
        if(fxmlFile.equals("home")){

            switch(user.getUserType()){
                case STUDENT -> {
                    return "home_student.fxml";
                }
                case TEACHER -> {
                    return "home_teacher.fxml";
                }
            }
        }

        //GROUPS
        if(fxmlFile.equals("groups")){

            if(user.getId() == session.getJoinedRoom().getOwner()){
                return "groups_owner.fxml";
            }

            switch(user.getUserType()){
                case STUDENT, TEACHER -> {
                    return "groups.fxml";
                }


            }
        }

        //TESTS
        if(fxmlFile.equals("tests")){
            switch(user.getUserType()){
                case STUDENT -> {
                    return "tests_students.fxml";
                }

                case TEACHER -> {
                    return "tests.fxml";
                }
            }
        }
        return fxmlFile;
    }
}
