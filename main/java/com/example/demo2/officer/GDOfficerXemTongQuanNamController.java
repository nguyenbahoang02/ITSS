package com.example.demo2.officer;

import com.example.demo2.configure.UserIdCurrent;
import com.example.demo2.configure.UserIdTable;
import com.example.demo2.configure.configureController.CurrentUser;
import com.example.demo2.entity.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class GDOfficerXemTongQuanNamController implements Initializable {
    @FXML
    private TableView<OfficerTimeSheetYear> table;
    @FXML
    private TableColumn<OfficerTimeSheetYear, String> monthCol;
    @FXML
    private TableColumn<OfficerTimeSheetYear, String> shiftCount;
    @FXML
    private TableColumn<OfficerTimeSheetYear, String> lateSoonHours;
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
    private Stage stage;
    private Scene scene;

    private  int[] years = {2020, 2021, 2022, 2023};
    private boolean isOpen = true;

    private ObservableList<OfficerTimeSheet> officerTimeSheetsList;
    private ObservableList<OfficerTimeSheetYear> officerTimeSheetYearObservableList;

    public List<OfficerTimeSheet> getDataToFile() {
        try {
            Gson gson = new Gson();
            Reader reader = Files.newBufferedReader(Paths.get("src\\main\\java\\com\\example\\demo2\\fileJSON\\officerTimeSheet.json"));
            List<OfficerTimeSheet> officerTimeSheetList = new Gson().fromJson(reader, new TypeToken<List<OfficerTimeSheet>>() {
            }.getType());
            List<OfficerTimeSheet> officerTimeSheetListFilter = new ArrayList<>();
            for (OfficerTimeSheet oneDay : officerTimeSheetList){
                if (oneDay.getOfficerId() == UserIdTable.getUserId()){
                    officerTimeSheetListFilter.add(oneDay);
                }
            }
            reader.close();
            return officerTimeSheetListFilter;

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
        if (CurrentUser.getCurrentEmployee() instanceof Manager){
            managerVbox.setVisible(true);
        }
        else if(CurrentUser.getCurrentEmployee() instanceof UnitLeaderOfficer || CurrentUser.getCurrentEmployee() instanceof UnitLeaderWorker) {
            unitLeaderVbox.setVisible(true);
        }
        closeApp.setOnMouseClicked(event -> {
            System.exit(0);
        });
        List<OfficerTimeSheet> officerTimeSheetList = this.getDataToFile();
        Collections.sort(officerTimeSheetList);
        List<OfficerTimeSheetYear> officerTimeSheetYears = new ArrayList<>();
        for (int year : years){
            officerTimeSheetYears.add(new OfficerTimeSheetYear(year,0,0));
        }
        for(OfficerTimeSheet oneDay : officerTimeSheetList){
            for (OfficerTimeSheetYear oneYear : officerTimeSheetYears){
                Date dateParse= null;
                try {
                    dateParse = new SimpleDateFormat("dd/MM/yyyy").parse(oneDay.getDate());
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                if ((dateParse.getYear() + 1900) == oneYear.getYear()){
                    if(oneDay.getMorning().equals("có")) oneYear.setShiftCount(oneYear.getShiftCount()+1);
                    if(oneDay.getAfternoon().equals("có")) oneYear.setShiftCount(oneYear.getShiftCount()+1);
                    oneYear.setLateSoonHours(oneYear.getLateSoonHours() + oneDay.getLateHours() + oneDay.getSoonHours());
                }
            }
        }
        officerTimeSheetYearObservableList = FXCollections.observableArrayList(
                officerTimeSheetYears
        );
        monthCol.setCellValueFactory(new PropertyValueFactory<OfficerTimeSheetYear, String>("year"));
        shiftCount.setCellValueFactory(new PropertyValueFactory<OfficerTimeSheetYear, String>("shiftCount"));
        lateSoonHours.setCellValueFactory(new PropertyValueFactory<OfficerTimeSheetYear, String>("lateSoonHours"));

        table.setItems(officerTimeSheetYearObservableList);

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
    }

    private void animationSidebar(double translateXSidebar, double translateXItem, double tableColumnWidth) {
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.5), sidebar);
        TranslateTransition translateTransitionTable = new TranslateTransition(Duration.seconds(0.5), table);

        translateTransition.setByX(translateXSidebar);
        translateTransitionTable.setByX(translateXItem);
        table.setPrefWidth(table.getPrefWidth() - translateXItem);
        monthCol.setPrefWidth(monthCol.getPrefWidth() + tableColumnWidth);
        shiftCount.setPrefWidth(shiftCount.getPrefWidth() + tableColumnWidth);
        lateSoonHours.setPrefWidth(lateSoonHours.getPrefWidth() + tableColumnWidth);
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
        String url = "/com/example/demo2/officer/GD-OfficerXemTongQuan";
        switchUrl(url, event);
    }
}
