package com.example.eksamens_vm;

import com.example.eksamens_vm.models.SceneHistory;
import com.example.eksamens_vm.services.RoomService;
import com.example.eksamens_vm.services.UserService;
import com.example.eksamens_vm.utils.SceneManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("login.fxml")));
        Scene scene = new Scene(root, 1024, 768);
        stage.setTitle("login");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        SceneManager.addToHistory(new SceneHistory("login.fxml", "login"));

    }

    public static void main(String[] args) {
        launch();
    }
}