package com.example.demo2.manager;

import com.example.demo2.Model.Officer.Officer;
import com.example.demo2.Model.Officer.UnitTimeSheetMonthOfficer;
import com.example.demo2.Model.Worker.Worker;
import com.example.demo2.configure.RangeTimeView;
import com.example.demo2.configure.UnitIdTable;
import com.example.demo2.configure.UserIdCurrent;
import com.example.demo2.configure.UserIdTable;
import com.example.demo2.configure.configureController.CurrentUser;
import com.example.demo2.Model.*;
import com.example.demo2.Model.Officer.OfficerTimeSheet;
import com.example.demo2.Model.Officer.UnitTimeSheetMonthOfficer;
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

public class GDListUnitTimeSheetMonthOfficer implements Initializable {
    @FXML
    private TableView<UnitTimeSheetMonthOfficer> table;
    @FXML
    private TableColumn<UnitTimeSheetMonthOfficer, String> monthCol;
    @FXML
    private TableColumn<UnitTimeSheetMonthOfficer, String> shiftCol;
    @FXML
    private TableColumn<UnitTimeSheetMonthOfficer, String> lateSoonHoursCol;
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
    @FXML private Text unitNameText;
    @FXML private Text unitLeaderText;
    @FXML private Text roleText;
    @FXML private Text employeeCountText;
    @FXML private AnchorPane information;
    private Stage stage;
    private Scene scene;

    private  Integer[] year = {2020, 2021, 2022, 2023};
    private boolean isOpen = true;

    private ObservableList<UnitTimeSheetMonthOfficer> UnitTimeSheetMonthOfficersList;

    private List<UnitTimeSheetMonthOfficer> createTimeSheetOfYear(int year){
        List<UnitTimeSheetMonthOfficer> UnitTimeSheetMonthOfficers = new ArrayList<>();
        for (int i = 1; i <= 12; i++){
            UnitTimeSheetMonthOfficers.add(new UnitTimeSheetMonthOfficer(year, i,0,0));
        }
        return UnitTimeSheetMonthOfficers;
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
    private List<UnitTimeSheetMonthOfficer> createNewTimeSheetChangeYear(int year){
        setSidebarInformation();
        List<OfficerTimeSheet> officerTimeSheetList = GetData.getAllOfficerTimeSheetToFile();
        Collections.sort(officerTimeSheetList);
        List<UnitTimeSheetMonthOfficer> UnitTimeSheetMonthOfficers = createTimeSheetOfYear(choiceBoxYear.getValue());
        for (OfficerTimeSheet oneDay : officerTimeSheetList){
            for (UnitTimeSheetMonthOfficer oneMonth : UnitTimeSheetMonthOfficers){
                Date dateParse= null;
                try {
                    dateParse = new SimpleDateFormat("dd/MM/yyyy").parse(oneDay.getDate());
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                Employee userOneDay = null;
                for (Employee e : GetData.getEmployeeToFile()){
                    if (e.getId() == oneDay.getOfficerId()) userOneDay = e;
                }
                if (UnitIdTable.getUnitId().equals(userOneDay.getUnitId()) && (dateParse.getYear() + 1900) == choiceBoxYear.getValue() && (dateParse.getMonth() + 1) == oneMonth.getMonth()){
                    if(oneDay.getMorning().equals("có")) oneMonth.setShiftCount(oneMonth.getShiftCount()+1);
                    if(oneDay.getAfternoon().equals("có")) oneMonth.setShiftCount(oneMonth.getShiftCount()+1);
                    oneMonth.setLateSoonHours(oneMonth.getLateSoonHours() + oneDay.getLateHours() + oneDay.getSoonHours());
                }
            }
        }
        return UnitTimeSheetMonthOfficers;
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
        for (Unit u : GetData.getUnitToFile())
            if (u.getId().equals(UnitIdTable.getUnitId())){
                unitNameText.setText(u.getName());
                employeeCountText.setText(""+u.getEmployeeCount());
                roleText.setText(u.getRole());
                unitLeaderText.setText(u.getLeaderName());
            }
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
        List<UnitTimeSheetMonthOfficer> UnitTimeSheetMonthOfficers = createNewTimeSheetChangeYear(choiceBoxYear.getValue());
        Collections.sort(UnitTimeSheetMonthOfficers);
        UnitTimeSheetMonthOfficersList = FXCollections.observableArrayList(
                UnitTimeSheetMonthOfficers
        );
        table.setItems(UnitTimeSheetMonthOfficersList);
        monthCol.setCellValueFactory(new PropertyValueFactory<UnitTimeSheetMonthOfficer, String>("month"));
        shiftCol.setCellValueFactory(new PropertyValueFactory<UnitTimeSheetMonthOfficer, String>("shiftCount"));
        lateSoonHoursCol.setCellValueFactory(new PropertyValueFactory<UnitTimeSheetMonthOfficer, String>("lateSoonHours"));
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
            List<UnitTimeSheetMonthOfficer> UnitTimeSheetMonthOfficersNew = createNewTimeSheetChangeYear(choiceBoxYear.getValue());
            Collections.sort(UnitTimeSheetMonthOfficersNew);
            UnitTimeSheetMonthOfficersList = FXCollections.observableArrayList(
                    UnitTimeSheetMonthOfficersNew
            );
            table.setItems(UnitTimeSheetMonthOfficersList);
            RangeTimeView.setYear(choiceBoxYear.getValue());
        });
        table.setRowFactory(tv -> {
            TableRow<UnitTimeSheetMonthOfficer> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (! row.isEmpty() && event.getButton()== MouseButton.PRIMARY
                        && event.getClickCount() == 2) {
                    UnitTimeSheetMonthOfficer clickedRow = row.getItem();
                    RangeTimeView.setMonthStart(clickedRow.getMonth());
                    RangeTimeView.setMonthEnd(clickedRow.getMonth());
                    Parent root = null;
                    try {
                        root = FXMLLoader.load(getClass().getResource("GD-Officer.fxml"));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    String css = this.getClass().getResource("GD-Officer.css").toExternalForm();
                    root.getStylesheets().add(css);
                    stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                }
            });
            return row ;
        });
    }

    private void animationSidebar(double translateXSidebar, double translateXItem, double tableColumnWidth) {
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.5), sidebar);
        TranslateTransition translateTransitionTable = new TranslateTransition(Duration.seconds(0.5), table);
        TranslateTransition translateTransitionYear = new TranslateTransition(Duration.seconds(0.5), choiceBoxYear);
        TranslateTransition translateTransitionInformation = new TranslateTransition(Duration.seconds(0.5), information);

        translateTransition.setByX(translateXSidebar);
        translateTransitionTable.setByX(translateXItem);
        translateTransitionYear.setByX(translateXItem);
        translateTransitionInformation.setByX(translateXItem);
        table.setPrefWidth(table.getPrefWidth() - translateXItem);
        monthCol.setPrefWidth(monthCol.getPrefWidth() + tableColumnWidth);
        shiftCol.setPrefWidth(shiftCol.getPrefWidth() + tableColumnWidth);
        lateSoonHoursCol.setPrefWidth(lateSoonHoursCol.getPrefWidth() + tableColumnWidth);
        translateTransition.play();
        translateTransitionTable.play();
        translateTransitionYear.play();
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
