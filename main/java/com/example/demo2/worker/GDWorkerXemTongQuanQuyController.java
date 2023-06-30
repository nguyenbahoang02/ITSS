package com.example.demo2.worker;

import com.example.demo2.Model.*;
import com.example.demo2.Model.Officer.Officer;
import com.example.demo2.Model.Officer.OfficerTimeSheet;
import com.example.demo2.Model.Worker.WorkerTimeSheet;
import com.example.demo2.Model.Worker.WorkerTimeSheetQuarter;
import com.example.demo2.Model.Worker.Worker;
import com.example.demo2.configure.RangeTimeView;
import com.example.demo2.configure.UserIdCurrent;
import com.example.demo2.configure.UserIdTable;
import com.example.demo2.configure.configureController.CurrentUser;
import com.example.demo2.configure.configureController.UserTableToView;
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

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

public class GDWorkerXemTongQuanQuyController implements Initializable {
    @FXML
    private TableView<WorkerTimeSheetQuarter> table;
    @FXML
    private TableColumn<WorkerTimeSheetQuarter, String> workHoursCol;
    @FXML
    private TableColumn<WorkerTimeSheetQuarter, String> quarterCol;
    @FXML
    private TableColumn<WorkerTimeSheetQuarter, String> overtimeHoursCol;
    @FXML
    private ChoiceBox<Integer> choiceBoxYear;
    @FXML
    private Label closeApp;
    @FXML
    private ImageView menuIcon;
    @FXML
    private ImageView menuIcon2;
    @FXML
    private AnchorPane sidebar;
    @FXML private VBox unitLeaderVbox;
    @FXML private VBox managerVbox;

    @FXML private AnchorPane clonePage;
    @FXML private AnchorPane requestPopup;
    @FXML private AnchorPane createRequest;
    @FXML private Label closeRequest;
    @FXML private Label closeCreate;
    @FXML private DatePicker datePicker;
    @FXML private TableView<Request> tableRequest;
    @FXML private TableColumn<Request, String> dateSendCol;
    @FXML private TableColumn<Request, String> statusCol;
    @FXML private TableColumn<Request, String> idRequestCol;
    @FXML private TextField messageTextField;
    @FXML private Label dateSendLabel;
    @FXML private Label senderNameLabel;
    @FXML private Label unitNameLabel;
    @FXML private Label dateLabel;
    @FXML private Label descriptionLabel;
    @FXML private AnchorPane requestDetail;
    @FXML private AnchorPane clonePage2;
    @FXML private Label closeRequestPopup;
    @FXML private Text userName;
    @FXML private Label unitName;
    @FXML private Text roleLabel;
    @FXML private Label userNameTable;
    @FXML private Label userIdTable;
    private Stage stage;
    private Scene scene;

    private  Integer[] year = {2020, 2021, 2022, 2023};
    private boolean isOpen = true;

    private ObservableList<WorkerTimeSheetQuarter> WorkerTimeSheetQuartersList;

    private List<WorkerTimeSheetQuarter> createTimeSheetOfYear(int year){
        List<WorkerTimeSheetQuarter> WorkerTimeSheetQuarters = new ArrayList<>();
        for (int i = 1; i <= 4; i++){
            WorkerTimeSheetQuarters.add(new WorkerTimeSheetQuarter(year, i,0,0));
        }
        return WorkerTimeSheetQuarters;
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

    private List<WorkerTimeSheetQuarter> createNewTimeSheetChangeYear(int year){
        setSidebarInformation();
        userIdTable.setText("     Mã nhân viên: "+UserIdTable.getUserId());
        userNameTable.setText("     Họ và tên: " + UserTableToView.getEmployee().getName());
        List<WorkerTimeSheet> workerTimeSheetList = GetData.getWorkerTimeShetToFile();
        Collections.sort(workerTimeSheetList);
        List<WorkerTimeSheetQuarter> WorkerTimeSheetQuarters = createTimeSheetOfYear(choiceBoxYear.getValue());
        for (WorkerTimeSheet oneDay : workerTimeSheetList){
            for (WorkerTimeSheetQuarter oneQuarter : WorkerTimeSheetQuarters){
                Date dateParse= null;
                try {
                    dateParse = new SimpleDateFormat("dd/MM/yyyy").parse(oneDay.getDate());
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                if ((dateParse.getYear() + 1900) == year && (dateParse.getMonth() + 1) > (oneQuarter.getQuarterCount()-1)*3 && (dateParse.getMonth() + 1) <= oneQuarter.getQuarterCount()*3){
                    oneQuarter.setWorkHours(oneDay.getWorkHoursShift1() + oneDay.getWorkHoursShift2() + oneQuarter.getWorkHours());
                    oneQuarter.setOvertimeHours(oneDay.getWorkHoursShift3() + oneQuarter.getOvertimeHours());
                }
            }
        }
        return WorkerTimeSheetQuarters;
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
        if (CurrentUser.getCurrentEmployee() instanceof Manager){
            managerVbox.setVisible(true);
        }
        else if(CurrentUser.getCurrentEmployee() instanceof UnitLeaderOfficer || CurrentUser.getCurrentEmployee() instanceof UnitLeaderWorker) {
            unitLeaderVbox.setVisible(true);
        }
        closeApp.setOnMouseClicked(event -> {
            System.exit(0);
        });

        choiceBoxYear.getItems().addAll(year);
        choiceBoxYear.setValue(year[3]);
        RangeTimeView.setYear(choiceBoxYear.getValue());
        List<WorkerTimeSheetQuarter> WorkerTimeSheetQuarters = createNewTimeSheetChangeYear(choiceBoxYear.getValue());
        Collections.sort(WorkerTimeSheetQuarters);
        WorkerTimeSheetQuartersList = FXCollections.observableArrayList(
                WorkerTimeSheetQuarters
        );
        table.setItems(WorkerTimeSheetQuartersList);
        quarterCol.setCellValueFactory(new PropertyValueFactory<WorkerTimeSheetQuarter, String>("quarterCount"));
        workHoursCol.setCellValueFactory(new PropertyValueFactory<WorkerTimeSheetQuarter, String>("workHours"));
        overtimeHoursCol.setCellValueFactory(new PropertyValueFactory<WorkerTimeSheetQuarter, String>("overtimeHours"));
        menuIcon.setOnMouseClicked(event -> {
            animationSidebar(-300, -200, 66.67);
            menuIcon2.setVisible(true);
            isOpen = false;
        });
        menuIcon2.setOnMouseClicked(event -> {
            menuIcon2.setVisible(false);
            animationSidebar(300, 200, -66.67);
            isOpen = true;
        });
        choiceBoxYear.setOnAction(event -> {
            List<WorkerTimeSheetQuarter> WorkerTimeSheetQuartersNew = createNewTimeSheetChangeYear(choiceBoxYear.getValue());
            Collections.sort(WorkerTimeSheetQuartersNew);
            WorkerTimeSheetQuartersList = FXCollections.observableArrayList(
                    WorkerTimeSheetQuartersNew
            );
            table.setItems(WorkerTimeSheetQuartersList);
            RangeTimeView.setYear(choiceBoxYear.getValue());
        });
        clonePage.setOnMouseClicked(mouseEvent -> {
            clonePage.setVisible(false);
            requestPopup.setVisible(false);
            createRequest.setVisible(false);
        });
        closeRequest.setOnMouseClicked(mouseEvent -> {
            clonePage.setVisible(false);
            requestPopup.setVisible(false);
        });
        closeCreate.setOnMouseClicked(mouseEvent -> {
            createRequest.setVisible(false);
        });
    }

    private void animationSidebar(double translateXSidebar, double translateXItem, double tableColumnWidth) {
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.5), sidebar);
        TranslateTransition translateTransitionTable = new TranslateTransition(Duration.seconds(0.5), table);
        TranslateTransition translateTransitionYear = new TranslateTransition(Duration.seconds(0.5), choiceBoxYear);

        translateTransition.setByX(translateXSidebar);
        translateTransitionTable.setByX(translateXItem);
        translateTransitionYear.setByX(translateXItem);
        table.setPrefWidth(table.getPrefWidth() - translateXItem);
        quarterCol.setPrefWidth(quarterCol.getPrefWidth() + tableColumnWidth);
        workHoursCol.setPrefWidth(workHoursCol.getPrefWidth() + tableColumnWidth);
        overtimeHoursCol.setPrefWidth(overtimeHoursCol.getPrefWidth() + tableColumnWidth);
        translateTransition.play();
        translateTransitionTable.play();
        translateTransitionYear.play();
    }

    public void switchToMonthView(ActionEvent event) throws IOException {
        String url = "/com/example/demo2/worker/GD-WorkerXemTongQuan";
        switchUrl(url, event);
    }
    public void switchToQuarterView(ActionEvent event) throws IOException {
        String url = "/com/example/demo2/worker/GD-WorkerXemTongQuanQuy";
        switchUrl(url, event);
    }
    public void switchToYearView(ActionEvent event) throws IOException {
        String url = "/com/example/demo2/worker/GD-WorkerXemTongQuanNam";
        switchUrl(url, event);
    }

    public void switchToCustomView(ActionEvent event) throws IOException {
        String url = "/com/example/demo2/worker/GD-WorkerXemTongQuanTuyChon";
        switchUrl(url, event);
    }
    public void switchToListEmployeeView(ActionEvent event) throws IOException {
        String url = "/com/example/demo2/unitLeader/GD-ListEmployeeOfUnit";
        switchUrl(url, event);
    }
    public void switchToCurrentView(ActionEvent event) throws IOException {
        UserIdTable.setUserId(UserIdCurrent.getUserId());
        String url = "/com/example/demo2/worker/GD-WorkerXemTongQuan";
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
    public void handleOpenRequestPopup(ActionEvent event) throws IOException{
        clonePage.setVisible(true);
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.2),clonePage);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(0.15);
        fadeTransition.play();
        requestPopup.setVisible(true);
        FadeTransition fadeTransition1 = new FadeTransition(Duration.seconds(0.2),requestPopup);
        fadeTransition1.setFromValue(0);
        fadeTransition1.setToValue(1);
        fadeTransition1.play();

        List<Request> requestList = GetData.getRequestToFile();
        Collections.sort(requestList);
        List<Request> requestListFilter = new ArrayList<>();
        for (Request oneRequest : requestList) {
            if (oneRequest.getSenderId() == UserIdCurrent.getUserId()) requestListFilter.add(oneRequest);
        }
        ObservableList<Request> requestObservableList = FXCollections.observableArrayList(requestListFilter);
        tableRequest.setItems(requestObservableList);
        dateSendCol.setCellValueFactory(new PropertyValueFactory<Request, String>("dateSend"));
        statusCol.setCellValueFactory(new PropertyValueFactory<Request, String>("status"));
        idRequestCol.setCellValueFactory(new PropertyValueFactory<Request, String>("id"));
        tableRequest.setRowFactory(tv -> {
            TableRow<Request> row = new TableRow<>();
            row.setOnMouseClicked(event2 -> {
                if (! row.isEmpty() && event2.getButton()== MouseButton.PRIMARY
                        && event2.getClickCount() == 2) {
                    Request clickedRow = row.getItem();
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
                    clonePage2.setVisible(true);
                    FadeTransition fadeTransition3 = new FadeTransition(Duration.seconds(0.2),clonePage2);
                    fadeTransition3.setFromValue(0);
                    fadeTransition3.setToValue(0.15);
                    fadeTransition3.play();
                    requestDetail.setVisible(true);
                    FadeTransition fadeTransition4 = new FadeTransition(Duration.seconds(0.2),requestDetail);
                    fadeTransition4.setFromValue(0);
                    fadeTransition4.setToValue(1);
                    fadeTransition4.play();
                    closeRequestPopup.setOnMouseClicked(mouseEvent -> {
                        requestDetail.setVisible(false);
                        clonePage2.setVisible(false);
                    });
                    clonePage2.setOnMouseClicked(mouseEvent -> {
                        requestDetail.setVisible(false);
                        clonePage2.setVisible(false);
                    });
                }
            });
            return row;
        });
    }
    public void handleOpenCreateRequestPopup(ActionEvent event) throws IOException{
        clonePage.setVisible(true);
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.2),clonePage);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(0.15);
        fadeTransition.play();
        createRequest.setVisible(true);
        FadeTransition fadeTransition1 = new FadeTransition(Duration.seconds(0.2),createRequest);
        fadeTransition1.setFromValue(0);
        fadeTransition1.setToValue(1);
        fadeTransition1.play();
    }
    public void handleSubmitCreateRequest(ActionEvent event) throws IOException{
        String datePickerValue = datePicker.getValue().toString();
        LocalDate currentDate = LocalDate.now();
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date date = inputFormat.parse(datePickerValue);
            String datePickerFormat = outputFormat.format(date);
            Date date2 = inputFormat.parse(currentDate.toString());
            String currentDateFormat = outputFormat.format(date2);
            Request requestNew = new Request(UserIdCurrent.getUserId(),datePickerFormat, messageTextField.getText(), "Chưa duyệt", currentDateFormat);
            List<Request> requestList = GetData.getRequestToFile();
            List<Request> requestListClone = new ArrayList<>();
            for (Request oneRequest : requestList) {
                requestListClone.add(new Request(oneRequest.getSenderId(), oneRequest.getDate(), oneRequest.getMessage(), oneRequest.getStatus(), oneRequest.getDateSend()));
            }
            requestListClone.add(requestNew);
            SetData.writeRequestToFile(requestListClone);
            ObservableList<Request> requestObservableList = FXCollections.observableArrayList(requestListClone);
            tableRequest.setItems(requestObservableList);
            createRequest.setVisible(false);
            requestPopup.setVisible(false);
            clonePage.setVisible(false);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}
