package com.example.demo2;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import  javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class HelloApplication extends Application {
    double x, y = 0;
    @Override
    public void start(Stage stage) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
//        String css = this.getClass().getResource("hello-view.css").toExternalForm();
//        scene.getStylesheets().add(css);

        Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        stage.initStyle(StageStyle.UNDECORATED);

        root.setOnMousePressed(event -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });

        root.setOnMouseDragged(event -> {
            stage.setX(event.getSceneX() - x);
            stage.setY(event.getSceneY() - y);
        });

        stage.setScene(new Scene(root, 800, 500));
        stage.show();
    }
    @FXML
    protected static void handleOnClick() {

    }

    public static void main(String[] args) {
        launch();
    }
}