package com.example.demo2.officer;

import com.example.demo2.configure.RangeTimeView;
import com.example.demo2.entity.OfficerTimeSheet;
import com.example.demo2.entity.OfficerTimeSheetMonth;
import com.example.demo2.entity.OfficerTimeSheetQuarter;
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
import javafx.scene.input.MouseButton;
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

public class GDOfficerXemTongQuanQuyController implements Initializable {
    @FXML
    private TableView<OfficerTimeSheetQuarter> table;
    @FXML
    private TableColumn<OfficerTimeSheetQuarter, String> monthCol;
    @FXML
    private TableColumn<OfficerTimeSheetQuarter, String> shiftCount;
    @FXML
    private TableColumn<OfficerTimeSheetQuarter, String> lateSoonHours;
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
    private Stage stage;
    private Scene scene;

    private  Integer[] year = {2020,2021,2022,2023};
    private boolean isOpen = true;

    private ObservableList<OfficerTimeSheetQuarter> officerTimeSheetsList;
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
    private List<OfficerTimeSheetQuarter> createTimeSheetOfYear(int year){
        List<OfficerTimeSheetQuarter> officerTimeSheetQuarters = new ArrayList<>();
        for (int i = 1; i <= 4; i++){
            officerTimeSheetQuarters.add(new OfficerTimeSheetQuarter(year, i,0,0));
        }
        return officerTimeSheetQuarters;
    }
    private List<OfficerTimeSheetQuarter> createNewTimeSheetChangeYear(int year){
        List<OfficerTimeSheet> officerTimeSheetList = this.getDataToFile();
        Collections.sort(officerTimeSheetList);
        List<OfficerTimeSheetQuarter> officerTimeSheetQuarters = createTimeSheetOfYear(year);
        for (OfficerTimeSheet oneDay : officerTimeSheetList){
            for (OfficerTimeSheetQuarter oneQuarter : officerTimeSheetQuarters){
                Date dateParse= null;
                try {
                    dateParse = new SimpleDateFormat("dd/MM/yyyy").parse(oneDay.getDate());
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                if ((dateParse.getYear() + 1900) == year && (dateParse.getMonth() + 1) > (oneQuarter.getQuarterCount()-1)*3 && (dateParse.getMonth() + 1) <= oneQuarter.getQuarterCount()*3){
                    if(oneDay.getMorning().equals("có")) oneQuarter.setShiftCount(oneQuarter.getShiftCount()+1);
                    if(oneDay.getAfternoon().equals("có")) oneQuarter.setShiftCount(oneQuarter.getShiftCount()+1);
                    oneQuarter.setLateSoonHours(oneQuarter.getLateSoonHours() + oneDay.getLateHours() + oneDay.getSoonHours());
                }
            }
        }
        return officerTimeSheetQuarters;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        closeApp.setOnMouseClicked(event -> {
            System.exit(0);
        });
        choiceBoxYear.getItems().addAll(year);
        choiceBoxYear.setValue(year[3]);
        RangeTimeView.setYear(choiceBoxYear.getValue());
        List<OfficerTimeSheetQuarter> officerTimeSheetQuarters = createNewTimeSheetChangeYear(choiceBoxYear.getValue());
        officerTimeSheetsList = FXCollections.observableArrayList(
                officerTimeSheetQuarters
        );
        table.setItems(officerTimeSheetsList);
        monthCol.setCellValueFactory(new PropertyValueFactory<OfficerTimeSheetQuarter, String>("quarterCount"));
        shiftCount.setCellValueFactory(new PropertyValueFactory<OfficerTimeSheetQuarter, String>("shiftCount"));
        lateSoonHours.setCellValueFactory(new PropertyValueFactory<OfficerTimeSheetQuarter, String>("lateSoonHours"));

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
            List<OfficerTimeSheetQuarter> officerTimeSheetQuartersNew = createNewTimeSheetChangeYear(choiceBoxYear.getValue());
            Collections.sort(officerTimeSheetQuartersNew);
            officerTimeSheetsList = FXCollections.observableArrayList(
                    officerTimeSheetQuartersNew
            );
            table.setItems(officerTimeSheetsList);
            RangeTimeView.setYear(choiceBoxYear.getValue());
        });
        table.setRowFactory(tv -> {
            TableRow<OfficerTimeSheetQuarter> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (! row.isEmpty() && event.getButton()== MouseButton.PRIMARY
                        && event.getClickCount() == 2) {
                    OfficerTimeSheetQuarter clickedRow = row.getItem();
                    RangeTimeView.setMonthStart((clickedRow.getQuarterCount()-1) * 3 + 1);
                    RangeTimeView.setMonthEnd(clickedRow.getQuarterCount()*3);
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
            return row;
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
        monthCol.setPrefWidth(monthCol.getPrefWidth() + tableColumnWidth);
        shiftCount.setPrefWidth(shiftCount.getPrefWidth() + tableColumnWidth);
        lateSoonHours.setPrefWidth(lateSoonHours.getPrefWidth() + tableColumnWidth);

        translateTransition.play();
        translateTransitionTable.play();
        translateTransitionYear.play();
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
