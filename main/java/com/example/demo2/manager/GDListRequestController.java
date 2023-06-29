package com.example.demo2.manager;

import com.example.demo2.Model.Officer.Officer;
import com.example.demo2.Model.Worker.Worker;
import com.example.demo2.configure.UserIdCurrent;
import com.example.demo2.configure.UserIdTable;
import com.example.demo2.configure.configureController.CurrentUser;
import com.example.demo2.Model.*;
import com.example.demo2.fileJSON.fileController.GetData;
import com.example.demo2.fileJSON.fileController.SetData;
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
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.*;
import java.io.*;

public class GDListRequestController implements Initializable {
    @FXML
    private TableView<Request> table;
    @FXML
    private TableColumn<Request, String> dateCol;
    @FXML
    private TableColumn<Request, String> senderIdCol;
    @FXML
    private TableColumn<Request, String> statusCol;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Label closeApp;
    @FXML
    private ImageView menuIcon;
    @FXML
    private ImageView menuIcon2;
    @FXML
    private AnchorPane sidebar;
    // pageClone
    @FXML private AnchorPane clonePage;
    @FXML private Label dateSendLabel;
    @FXML private Label senderNameLabel;
    @FXML private Label unitNameLabel;
    @FXML private Label dateLabel;
    @FXML private Label descriptionLabel;
    @FXML private Button rejectRequestBtn;
    @FXML private Button acceptRequestBtn;
    @FXML private AnchorPane popup;
    @FXML private Label closePopup;
    @FXML private VBox unitLeaderVbox;
    @FXML private VBox managerVbox;
    @FXML private Text userName;
    @FXML private Label unitName;
    @FXML private Text roleLabel;
    // Request
    private Stage stage;
    private Scene scene;
    private  String[] month = {"tháng 1","tháng 2", "tháng 3", "tháng 4", "tháng 5", "tháng 6", "tháng 7", "tháng 8", "tháng 9", "tháng 10", "tháng 11", "tháng 12"};
    private  String[] year = {"2020", "2021", "2022", "2023"};
    private int page = 1;
    private boolean isOpen = true;

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
        closeApp.setOnMouseClicked(event -> {
            System.exit(0);
        });

        List<Request> requestList = GetData.getRequestToFile();
        Collections.sort(requestList);

        ObservableList<Request> requestObservableList = FXCollections.observableArrayList(requestList);
        table.setItems(requestObservableList);
        dateCol.setCellValueFactory(new PropertyValueFactory<Request, String>("dateSend"));
        statusCol.setCellValueFactory(new PropertyValueFactory<Request, String>("status"));
        senderIdCol.setCellValueFactory(new PropertyValueFactory<Request, String>("id"));

        menuIcon.setOnMouseClicked(event -> {
            animationSidebar(-300, -200, 40);
            menuIcon2.setVisible(true);
            isOpen = false;
        });
        menuIcon2.setOnMouseClicked(event -> {
            menuIcon2.setVisible(false);
            animationSidebar(300, 200, -40);
            isOpen = true;
        });

        table.setRowFactory(tv -> {
            TableRow<Request> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (! row.isEmpty() && event.getButton()== MouseButton.PRIMARY
                        && event.getClickCount() == 2) {
                    Request clickedRow = row.getItem();
                    if (!clickedRow.getStatus().equals("Chưa duyệt")){
                        acceptRequestBtn.setVisible(false);
                        rejectRequestBtn.setVisible(false);
                    }
                    else {
                        acceptRequestBtn.setVisible(true);
                        rejectRequestBtn.setVisible(true);
                    }
                    dateSendLabel.setText(clickedRow.getDateSend());
                    for (Employee e : GetData.getEmployeeToFile()) {
                        if (e.getId() == clickedRow.getSenderId()){
                            senderNameLabel.setText(e.getName());
                            for (Unit u : GetData.getUnitToFile()) {
                                if (u.getId().equals(e.getUnitId())) unitNameLabel.setText(u.getName());
                            }
                        }
                    }
                    dateLabel.setText(clickedRow.getDate());
                    descriptionLabel.setText(clickedRow.getMessage());
                    acceptRequestBtn.setOnMouseClicked(mouseEvent -> {
                        for (Request oneRequest : requestList) {
                            if (oneRequest.getId() == clickedRow.getId()){
                                oneRequest.setStatus("Đồng ý");
                            }
                        }
                        SetData.writeRequestToFile(requestList);
                        ObservableList<Request> requestObservableListNew = FXCollections.observableArrayList(GetData.getRequestToFile());
                        table.setItems(requestObservableListNew);
                        popup.setVisible(false);
                        clonePage.setVisible(false);
                    });
                    rejectRequestBtn.setOnMouseClicked(mouseEvent -> {
                        for (Request oneRequest : requestList) {
                            if (oneRequest.getId() == clickedRow.getId()){
                                oneRequest.setStatus("Từ chối");
                            }
                        }
                        SetData.writeRequestToFile(requestList);
                        ObservableList<Request> requestObservableListNew = FXCollections.observableArrayList(requestList);
                        table.setItems(requestObservableListNew);
                        popup.setVisible(false);
                        clonePage.setVisible(false);
                    });
                    clonePage.setVisible(true);
                    FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.2),clonePage);
                    fadeTransition.setFromValue(0);
                    fadeTransition.setToValue(0.15);
                    fadeTransition.play();
                    popup.setVisible(true);
                    FadeTransition fadeTransition1 = new FadeTransition(Duration.seconds(0.2),popup);
                    fadeTransition1.setFromValue(0);
                    fadeTransition1.setToValue(1);
                    fadeTransition1.play();
                }
            });
            return row;
        });
        clonePage.setOnMouseClicked(mouseEvent -> {
            clonePage.setVisible(false);
            popup.setVisible(false);
        });
        closePopup.setOnMouseClicked(mouseEvent -> {
            clonePage.setVisible(false);
            popup.setVisible(false);
        });
    }

    private void animationSidebar(double translateXSidebar, double translateXItem, double tableColumnWidth) {
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.5), sidebar);
        TranslateTransition translateTransitionTable = new TranslateTransition(Duration.seconds(0.5), table);

        translateTransition.setByX(translateXSidebar);
        translateTransitionTable.setByX(translateXItem);
        table.setPrefWidth(table.getPrefWidth() - translateXItem);

        dateCol.setPrefWidth(dateCol.getPrefWidth() + tableColumnWidth);
        senderIdCol.setPrefWidth(senderIdCol.getPrefWidth() + tableColumnWidth);
        statusCol.setPrefWidth(statusCol.getPrefWidth() + tableColumnWidth);


        translateTransition.play();
        translateTransitionTable.play();

    }

    public void switchToMonthView(ActionEvent event) throws IOException {
        String url = "GD-OfficerXemTongQuan";
        switchUrl(url, event);
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
    public void switchToListUnit(ActionEvent event) throws IOException{
        String url = "/com/example/demo2/manager/GD-ListUnitView";
        switchUrl(url, event);
    }
    public void switchToRequestView(ActionEvent event) throws IOException{
        String url = "/com/example/demo2/manager/GD-ListRequestView";
        switchUrl(url, event);
    }

}
