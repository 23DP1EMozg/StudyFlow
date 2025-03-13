package com.example.eksamens_vm.utils;

import com.example.eksamens_vm.models.SceneHistory;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SceneManager {

    private static List<SceneHistory> history = new ArrayList<>();

    public SceneManager() {
        history.add(new SceneHistory("login.fxml", "login"));
    }

    public static void switchScenes(ActionEvent event, String fxmlFile, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource("/com/example/eksamens_vm/" + fxmlFile));
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

    public static void goBack(ActionEvent event) {
        if (history.size() >= 2) {
            history.removeLast();
            SceneHistory sceneHistory = history.getLast();
            switchScenes(event, sceneHistory.getFxmlFile(), sceneHistory.getTitle());
        } else {
            System.out.println("no history!");
        }
    }

    public static void addToHistory(SceneHistory sceneHistory) {
        history.add(sceneHistory);
    }

    public static void clearHistory(){
        history.clear();
    }
}
