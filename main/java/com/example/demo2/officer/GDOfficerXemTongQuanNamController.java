package com.example.demo2.officer;

import com.example.demo2.entity.OfficerTimeSheet;
import com.example.demo2.entity.OfficerTimeSheetYear;
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
            reader.close();
            return officerTimeSheetList;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
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
        Parent root = FXMLLoader.load(getClass().getResource("GD-OfficerXemTongQuan.fxml"));
        String css = this.getClass().getResource("GD-OfficerXemTongQuan.css").toExternalForm();
        root.getStylesheets().add(css);
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void switchToQuarterView(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("GD-OfficerXemTongQuanQuy.fxml"));
        String css = this.getClass().getResource("GD-OfficerXemTongQuanQuy.css").toExternalForm();
        root.getStylesheets().add(css);
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void switchToYearView(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("GD-OfficerXemTongQuanNam.fxml"));
        String css = this.getClass().getResource("GD-OfficerXemTongQuanNam.css").toExternalForm();
        root.getStylesheets().add(css);
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToCustomView(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("GD-OfficerXemTongQuanTuyChon.fxml"));
        String css = this.getClass().getResource("GD-OfficerXemTongQuanTuyChon.css").toExternalForm();
        root.getStylesheets().add(css);
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
