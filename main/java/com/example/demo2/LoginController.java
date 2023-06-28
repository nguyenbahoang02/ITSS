package com.example.demo2;

import com.example.demo2.configure.UserIdCurrent;
import com.example.demo2.configure.UserIdTable;
import com.example.demo2.entity.Employee;
import com.example.demo2.entity.Officer;
import com.example.demo2.entity.UnitLeaderOfficer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class LoginController {
    @FXML private TextField idTextField;
    private Stage stage;
    private Scene scene;
    public Employee getCurrentEmployee () {
        try {
            UserIdCurrent.setUserId(Integer.parseInt(idTextField.getText()));
            List<Employee> employeeList = new ArrayList<>();
            Reader reader = Files.newBufferedReader(Paths.get("src\\main\\java\\com\\example\\demo2\\fileJSON\\officer.json"));
            List<Officer> oficerList = new Gson().fromJson(reader, new TypeToken<List<Officer>>() {
            }.getType());
            Reader reader2 = Files.newBufferedReader(Paths.get("src\\main\\java\\com\\example\\demo2\\fileJSON\\unitLeaderOfficer.json"));
            List<UnitLeaderOfficer> unitLeaderOfficerList = new Gson().fromJson(reader2, new TypeToken<List<UnitLeaderOfficer>>() {
            }.getType());
            employeeList.addAll(oficerList);
            employeeList.addAll(unitLeaderOfficerList);
            for (Employee employee : employeeList) {
                if (employee.getId() == UserIdCurrent.getUserId()) {
                    return  employee;
                }
            }
            reader.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    public void handleSubmit(ActionEvent event) throws IOException {
        if (getCurrentEmployee() != null) UserIdTable.setUserId(Integer.parseInt(idTextField.getText()));
        if(getCurrentEmployee() instanceof Officer || getCurrentEmployee() instanceof UnitLeaderOfficer){
            Parent root = FXMLLoader.load(getClass().getResource("officer/GD-OfficerXemTongQuan.fxml"));
            String css = this.getClass().getResource("officer/GD-OfficerXemTongQuan.css").toExternalForm();
            root.getStylesheets().add(css);
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

    }
}
