package com.example.demo2;

import com.example.demo2.configure.UnitIdTable;
import com.example.demo2.configure.UserIdCurrent;
import com.example.demo2.configure.UserIdTable;
import com.example.demo2.configure.configureController.CurrentUser;
import com.example.demo2.Model.*;
import com.example.demo2.Model.Officer.Officer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    @FXML private TextField idTextField;
    private Stage stage;
    private Scene scene;
    private void switchUrl(String url, ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(url + ".fxml"));
        String css = this.getClass().getResource(url + ".css").toExternalForm();
        root.getStylesheets().add(css);
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void handleSubmit(ActionEvent event) throws IOException {
        UserIdCurrent.setUserId(Integer.parseInt(idTextField.getText()));
        if (CurrentUser.getCurrentEmployee() != null) UserIdTable.setUserId(Integer.parseInt(idTextField.getText()));
        if(CurrentUser.getCurrentEmployee() instanceof UnitLeaderOfficer || CurrentUser.getCurrentEmployee() instanceof UnitLeaderWorker)
            UnitIdTable.setUnitId(CurrentUser.getCurrentEmployee().getUnitId());
        if(CurrentUser.getCurrentEmployee() instanceof Officer || CurrentUser.getCurrentEmployee() instanceof UnitLeaderOfficer || CurrentUser.getCurrentEmployee() instanceof  Manager){
            String url = "officer/GD-OfficerXemTongQuan";
            switchUrl(url, event);
        } else {
            String url = "worker/GD-WorkerXemTongQuan";
            switchUrl(url, event);
        }
    }
}
