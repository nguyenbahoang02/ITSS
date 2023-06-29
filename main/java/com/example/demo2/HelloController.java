package com.example.demo2;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import  java.net.URL;
import  java.util.ResourceBundle;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.transform.Translate;
import javafx.util.Duration;

import java.util.ResourceBundle;

public class HelloController implements Initializable {
//    @FXML
//    private Label welcomeText;
//
//    @FXML
//    private Label welcome2;
//
//    @FXML
//    protected void onHelloButtonClick() {
//        welcomeText.setText("Welcome to JavaFX Application!");
//    }
//
//    @FXML
//    protected void onClick() { welcome2.setText("hello");}
    @FXML
    private AnchorPane pane1, pane2;
    @FXML
    private Button menu;


    @Override
    public void initialize(URL location, ResourceBundle resources){
        pane1.setVisible(false);

        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.5),pane1);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.play();

        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.5),pane2);
        translateTransition.setByX(-600);
        translateTransition.play();

        menu.setOnMouseClicked(event -> {
            pane1.setVisible(true);

            FadeTransition fadeTransition1 = new FadeTransition(Duration.seconds(0.5),pane1);
            fadeTransition1.setFromValue(0);
            fadeTransition1.setToValue(0.15);
            fadeTransition1.play();

            TranslateTransition translateTransition1 = new TranslateTransition(Duration.seconds(0.5), pane2);
            translateTransition1.setByX(600);
            translateTransition1.play();
        });

        pane1.setOnMouseClicked(event -> {
            FadeTransition fadeTransition1 = new FadeTransition(Duration.seconds(0.5),pane1);
            fadeTransition1.setToValue(0.15);
            fadeTransition1.setToValue(0);
            fadeTransition1.play();

            fadeTransition1.setOnFinished(event1 -> pane1.setVisible(false));

            TranslateTransition translateTransition1 = new TranslateTransition(Duration.seconds(0.5), pane2);
            translateTransition1.setByX(-600);
            translateTransition1.play();
        });
    }

}