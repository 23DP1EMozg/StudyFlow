package com.example.eksamens_vm.utils;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SceneManager {

    private static List<String> history = new ArrayList<>();

    public SceneManager() {
        history.add("login.fxml");
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
            history.add(fxmlFile);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void goBack(ActionEvent event) {}
}
