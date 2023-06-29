package com.example.demo2.officer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class GDOfficerApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("GD-ListEmployee.fxml"));
        String css = this.getClass().getResource("GD-OfficerXemTongQuan.css").toExternalForm();
        root.getStylesheets().add(css);
        primaryStage.setScene(new Scene(root, 1280, 720));
        primaryStage.show();
    }
}
