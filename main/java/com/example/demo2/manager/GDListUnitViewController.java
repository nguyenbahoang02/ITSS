package com.example.demo2.manager;

import com.example.demo2.Model.Officer.Officer;
import com.example.demo2.Model.Worker.Worker;
import com.example.demo2.configure.UnitIdTable;
import com.example.demo2.configure.UserIdCurrent;
import com.example.demo2.configure.UserIdTable;
import com.example.demo2.configure.configureController.CurrentUser;
import com.example.demo2.Model.*;
import com.example.demo2.fileJSON.fileController.GetData;
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
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class GDListUnitViewController implements Initializable {
    @FXML
    private TableView<Unit> table;
    @FXML private TableColumn<Unit, String> indexCol;
    @FXML private TableColumn<Unit, String> unitCol;
    @FXML private TableColumn<Unit, String> roleCol;
    @FXML private TableColumn<Unit, String> employeeCountCol;
    @FXML private TableColumn<Unit, String> unitLeaderCol;
    @FXML
    private ImageView menuIcon;
    @FXML
    private ImageView menuIcon2;
    @FXML
    private AnchorPane sidebar;
    @FXML private TextField searchTextField;
    @FXML private VBox unitLeaderVbox;
    @FXML private VBox managerVbox;
    @FXML private Text userName;
    @FXML private Label unitName;
    @FXML private Text roleLabel;
    private Stage stage;
    private Scene scene;

    private ObservableList<Unit> unitObservableList;

    private void switchUrl(String url, ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(url + ".fxml"));
        String css = this.getClass().getResource(url + ".css").toExternalForm();
        root.getStylesheets().add(css);
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    private void setSidebarInformation() {
        Employee currentEmployee = CurrentUser.getCurrentEmployee();
        userName.setText(currentEmployee.getName());
        for (Unit u : GetData.getUnitToFile())
            if (u.getId().equals(currentEmployee.getUnitId()))
                unitName.setText(u.getName());
        if (currentEmployee instanceof Manager) {
            roleLabel.setText("Người quản lý");
        }
        else if (currentEmployee instanceof UnitLeaderOfficer || currentEmployee instanceof UnitLeaderWorker){
            roleLabel.setText("Trưởng đơn vị");
        }
        else if (currentEmployee instanceof Worker) {
            roleLabel.setText("Công nhân");
        }
        else if (currentEmployee instanceof Officer) {
            roleLabel.setText("Nhân viên văn phòng");
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setSidebarInformation();
        if (CurrentUser.getCurrentEmployee() instanceof Manager){
            managerVbox.setVisible(true);
        }
        else if(CurrentUser.getCurrentEmployee() instanceof UnitLeaderOfficer || CurrentUser.getCurrentEmployee() instanceof UnitLeaderWorker) {
            unitLeaderVbox.setVisible(true);
        }
        List<Unit> unitList = GetData.getUnitToFile();
        Collections.sort(unitList);
        unitObservableList = FXCollections.observableArrayList(
                unitList
        );
        table.setItems(unitObservableList);
        indexCol.setCellValueFactory(new PropertyValueFactory<Unit, String>("id"));
        unitCol.setCellValueFactory(new PropertyValueFactory<Unit, String>("name"));
        roleCol.setCellValueFactory(new PropertyValueFactory<Unit, String>("role"));
        employeeCountCol.setCellValueFactory(new PropertyValueFactory<Unit, String>("employeeCount"));
        unitLeaderCol.setCellValueFactory(new PropertyValueFactory<Unit, String>("leaderName"));
        menuIcon.setOnMouseClicked(event -> {
            animationSidebar(-300, -200, 40);
            menuIcon2.setVisible(true);
        });
        menuIcon2.setOnMouseClicked(event -> {
            menuIcon2.setVisible(false);
            animationSidebar(300, 200, -40);
        });
        table.setRowFactory(tv -> {
            TableRow<Unit> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (! row.isEmpty() && event.getButton()== MouseButton.PRIMARY
                        && event.getClickCount() == 2) {
                    Unit clickedRow = row.getItem();
                    UnitIdTable.setUnitId(clickedRow.getId());
                    Parent root = null;
                    if (clickedRow.getRole().equals("worker")){
                        try {
                            root = FXMLLoader.load(getClass().getResource("/com/example/demo2/manager/GD-ListUnitTimeSheetMonthWorker.fxml"));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        String css = this.getClass().getResource("/com/example/demo2/manager/GD-ListUnitTimeSheetMonthWorker.css").toExternalForm();
                        root.getStylesheets().add(css);
                        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                        scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();
                    } else {
                        try {
                            root = FXMLLoader.load(getClass().getResource("/com/example/demo2/manager/GD-ListUnitTimeSheetMonthOfficer.fxml"));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        String css = this.getClass().getResource("/com/example/demo2/manager/GD-ListUnitTimeSheetMonthOfficer.css").toExternalForm();
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
//        lateSoonHours.setPrefWidth(lateSoonHours.getPrefWidth() + tableColumnWidth);
        translateTransition.play();
        translateTransitionTable.play();
    }

    public void switchToMonthView(ActionEvent event) throws IOException {
        String url = "GD-OfficerXemTongQuan";
        switchUrl(url, event);
    }
    public void switchToQuarterView(ActionEvent event) throws IOException {
        String url = "GD-OfficerXemTongQuanQuy";
        switchUrl(url, event);
    }
    public void switchToYearView(ActionEvent event) throws IOException {
        String url = "GD-OfficerXemTongQuanNam";
        switchUrl(url, event);
    }

    public void switchToCustomView(ActionEvent event) throws IOException {
        String url = "GD-OfficerXemTongQuanTuyChon";
        switchUrl(url, event);
    }
    public void switchToListEmployeeView(ActionEvent event) throws IOException {
        String url = "/com/example/demo2/unitLeader/GD-ListEmployeeOfUnit";
        switchUrl(url, event);
    }
    public void switchToCurrentView(ActionEvent event) throws IOException {
        UserIdTable.setUserId(UserIdCurrent.getUserId());
        Employee currentUser = CurrentUser.getCurrentEmployee();
        if (currentUser instanceof Worker ){
            String url = "/com/example/demo2/worker/GD-WorkerXemTongQuan";
            switchUrl(url, event);
        } else {
            String url = "/com/example/demo2/officer/GD-OfficerXemTongQuan";
            switchUrl(url, event);
        }
    }
    public void switchToLoginView(ActionEvent event) throws IOException {
        String url = "/com/example/demo2/login-view";
        switchUrl(url, event);
    }
    public void switchToListUnit(ActionEvent event) throws IOException{
        String url = "/com/example/demo2/manager/GD-ListUnitView";
        switchUrl(url, event);
    }
    public void switchToRequestView(ActionEvent event) throws IOException{
        String url = "/com/example/demo2/manager/GD-ListRequestView";
        switchUrl(url, event);
    }
}
