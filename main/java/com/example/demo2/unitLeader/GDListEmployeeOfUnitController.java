package com.example.demo2.unitLeader;

import com.example.demo2.configure.RangeTimeView;
import com.example.demo2.configure.UserIdCurrent;
import com.example.demo2.configure.UserIdTable;
import com.example.demo2.configure.configureController.CurrentUser;
import com.example.demo2.entity.*;
import com.google.gson.reflect.TypeToken;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import com.google.gson.*;

import javax.crypto.MacSpi;
import java.io.*;
import java.nio.file.*;

public class GDListEmployeeOfUnitController implements Initializable {
    @FXML
    private TableView<Employee> table;
    @FXML
    private ImageView menuIcon;
    @FXML
    private  ImageView menuIcon2;
    @FXML
    private AnchorPane sidebar;
    @FXML private TableColumn<Employee, String> indexCol;
    @FXML private TableColumn<Employee, String> userIdCol;
    @FXML private TableColumn<Employee, String> nameCol;
    @FXML private TextField searchInput;
    private Stage stage;
    private Scene scene;
    private ObservableList<Employee> employeeObservableList;

    public List<Employee> getDataToFile() {
        try {
            List<Employee> employeeList = new ArrayList<>();
            Reader reader = Files.newBufferedReader(Paths.get("src\\main\\java\\com\\example\\demo2\\fileJSON\\officer.json"));
            List<Officer> oficerList = new Gson().fromJson(reader, new TypeToken<List<Officer>>() {
            }.getType());
            employeeList.addAll(oficerList);
            reader.close();
            return employeeList;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    private void switchUrl(String url, ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(url + ".fxml"));
        String css = this.getClass().getResource(url + ".css").toExternalForm();
        root.getStylesheets().add(css);
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<Employee> employeeList = getDataToFile();
        Collections.sort(employeeList);
        List<Employee> employeeListFilterUnit = new ArrayList<>();
        Employee currentEmployee = CurrentUser.getCurrentEmployee();
        for (Employee employee : employeeList){
            if (employee.getUnitId().equals(currentEmployee.getUnitId())) employeeListFilterUnit.add(employee);
        }
        employeeObservableList = FXCollections.observableArrayList(employeeListFilterUnit);
        table.setItems(employeeObservableList);
        indexCol.setCellValueFactory(new PropertyValueFactory<Employee, String>("id"));
        userIdCol.setCellValueFactory(new PropertyValueFactory<Employee, String>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<Employee, String>("name"));
        menuIcon.setOnMouseClicked(event -> {
            animationSidebar(-300, -200, 66.67);
            menuIcon2.setVisible(true);
        });
        menuIcon2.setOnMouseClicked(event -> {
            menuIcon2.setVisible(false);
            animationSidebar(300, 200, -66.67);
        });
        searchInput.setOnAction(event -> {
            List<Employee> employeeListNew = new ArrayList<>();
            for(Employee employee : employeeListFilterUnit){
                if(employee.getName().toLowerCase().contains(searchInput.getText().toLowerCase())){
                    employeeListNew.add(employee);
                }
            }
            employeeObservableList = FXCollections.observableArrayList(employeeListNew);
            table.setItems(employeeObservableList);
        });
        table.setRowFactory(tv -> {
            TableRow<Employee> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (! row.isEmpty() && event.getButton()== MouseButton.PRIMARY
                        && event.getClickCount() == 2) {
                    Employee clickedRow = row.getItem();
                    UserIdTable.setUserId(clickedRow.getId());
                    Employee employee = null;
                    for (Employee e : employeeList){
                        if (e.getId() == clickedRow.getId()) employee = e;
                    }
                    if (employee instanceof Officer){
                        Parent root = null;
                        try {
                            root = FXMLLoader.load(getClass().getResource("/com/example/demo2/officer/GD-OfficerXemTongQuan.fxml"));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        String css = this.getClass().getResource("/com/example/demo2/officer/GD-OfficerXemTongQuan.css").toExternalForm();
                        root.getStylesheets().add(css);
                        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                        scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();
                    }
                }
            });
            return row;
        });

    }

    private void animationSidebar(double translateXSidebar, double translateXItem, double tableColumnWidth) {
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.5), sidebar);
        TranslateTransition translateTransitionTable = new TranslateTransition(Duration.seconds(0.5), table);

        translateTransition.setByX(translateXSidebar);
        translateTransitionTable.setByX(translateXItem);
        table.setPrefWidth(table.getPrefWidth() - translateXItem);

        indexCol.setPrefWidth(indexCol.getPrefWidth() + tableColumnWidth);
        userIdCol.setPrefWidth(userIdCol.getPrefWidth() + tableColumnWidth);
        nameCol.setPrefWidth(nameCol.getPrefWidth() + tableColumnWidth);


        translateTransition.play();
        translateTransitionTable.play();

    }
    public void switchToListEmployeeView(ActionEvent event) throws IOException {
        String url = "/com/example/demo2/unitLeader/GD-ListEmployeeOfUnit";
        switchUrl(url, event);
    }
    public void switchToCurrentView(ActionEvent event) throws IOException {
        UserIdTable.setUserId(UserIdCurrent.getUserId());
        String url = "/com/example/demo2/officer/GD-OfficerXemTongQuan";
        switchUrl(url, event);
    }
    public void switchToLoginView(ActionEvent event) throws IOException {
        String url = "/com/example/demo2/login-view";
        switchUrl(url, event);
    }
}
