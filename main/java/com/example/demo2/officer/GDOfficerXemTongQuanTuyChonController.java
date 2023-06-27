package com.example.demo2.officer;

import com.example.demo2.configure.RangeTimeView;
import com.example.demo2.entity.OfficerTimeSheet;
import com.example.demo2.entity.OfficerTimeSheetCustom;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class GDOfficerXemTongQuanTuyChonController implements Initializable {
    @FXML
    private TableView<OfficerTimeSheetCustom> table;
    @FXML
    private TableColumn<OfficerTimeSheetCustom, String> shiftCount;
    @FXML
    private TableColumn<OfficerTimeSheetCustom, String> lateSoonHours;
    @FXML
    private ChoiceBox<Integer> choiceBoxYear;
    @FXML
    private ChoiceBox<String> choiceBoxMonth1;
    @FXML
    private ChoiceBox<String> choiceBoxMonth2;
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
    private  String[] month = {"tháng 1","tháng 2", "tháng 3", "tháng 4", "tháng 5", "tháng 6", "tháng 7", "tháng 8", "tháng 9", "tháng 10", "tháng 11", "tháng 12"};
    private  Integer[] year = {2020, 2021, 2022, 2023};
    private boolean isOpen = true;

    private ObservableList<OfficerTimeSheetCustom> officerTimeSheetsList;

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
    private List<OfficerTimeSheetCustom> createNewTimeSheetChangeYear(int year, int monthStart, int monthEnd){
        List<OfficerTimeSheet> officerTimeSheetList = this.getDataToFile();
        Collections.sort(officerTimeSheetList);
        List<OfficerTimeSheetCustom> officerTimeSheetCustoms = new ArrayList<>();
        officerTimeSheetCustoms.add(new OfficerTimeSheetCustom(monthStart, monthEnd, year, 0, 0));
        for (OfficerTimeSheet oneDay : officerTimeSheetList){
            for (OfficerTimeSheetCustom oneCustom : officerTimeSheetCustoms){
                Date dateParse= null;
                try {
                    dateParse = new SimpleDateFormat("dd/MM/yyyy").parse(oneDay.getDate());
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                if ((dateParse.getYear() + 1900) == year && (dateParse.getMonth() + 1) >= oneCustom.getMonthStart() && (dateParse.getMonth() + 1) <= oneCustom.getMonthEnd()){
                    if(oneDay.getMorning().equals("có")) oneCustom.setShiftCount(oneCustom.getShiftCount()+1);
                    if(oneDay.getAfternoon().equals("có")) oneCustom.setShiftCount(oneCustom.getShiftCount()+1);
                    oneCustom.setLateSoonHours(oneCustom.getLateSoonHours() + oneDay.getLateHours() + oneDay.getSoonHours());
                }
            }
        }
        return officerTimeSheetCustoms;
    }
    public static int getMonthNumber(String monthString) {
        String numericString = monthString.replaceAll("\\D+", "");
        try {
            return Integer.parseInt(numericString);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        closeApp.setOnMouseClicked(event -> {
            System.exit(0);
        });
        choiceBoxYear.getItems().addAll(year);
        choiceBoxYear.setValue(year[3]);
        choiceBoxMonth1.getItems().addAll(month);
        choiceBoxMonth2.getItems().addAll(month);
        List<OfficerTimeSheet> officerTimeSheetList = this.getDataToFile();
        Collections.sort(officerTimeSheetList);
        shiftCount.setCellValueFactory(new PropertyValueFactory<OfficerTimeSheetCustom, String>("shiftCount"));
        lateSoonHours.setCellValueFactory(new PropertyValueFactory<OfficerTimeSheetCustom, String>("lateSoonHours"));
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
            if(choiceBoxYear.getValue() != null && choiceBoxMonth1.getValue() != null && choiceBoxMonth2.getValue() != null){
                int startMonth = getMonthNumber(choiceBoxMonth1.getValue());
                int endMonth = getMonthNumber(choiceBoxMonth2.getValue());
                List<OfficerTimeSheetCustom> officerTimeSheetCustoms = createNewTimeSheetChangeYear(choiceBoxYear.getValue(), startMonth, endMonth);
                officerTimeSheetsList = FXCollections.observableArrayList(
                        officerTimeSheetCustoms
                );
                table.setItems(officerTimeSheetsList);
            }
        });
        choiceBoxMonth1.setOnAction(event -> {
            if(choiceBoxYear.getValue() != null && choiceBoxMonth1.getValue() != null && choiceBoxMonth2.getValue() != null){
                int startMonth = getMonthNumber(choiceBoxMonth1.getValue());
                int endMonth = getMonthNumber(choiceBoxMonth2.getValue());
                List<OfficerTimeSheetCustom> officerTimeSheetCustoms = createNewTimeSheetChangeYear(choiceBoxYear.getValue(), startMonth, endMonth);
                officerTimeSheetsList = FXCollections.observableArrayList(
                        officerTimeSheetCustoms
                );
                table.setItems(officerTimeSheetsList);
            }
        });
        choiceBoxMonth2.setOnAction(event -> {
            if(choiceBoxYear.getValue() != null && choiceBoxMonth1.getValue() != null && choiceBoxMonth2.getValue() != null){
                int startMonth = getMonthNumber(choiceBoxMonth1.getValue());
                int endMonth = getMonthNumber(choiceBoxMonth2.getValue());
                List<OfficerTimeSheetCustom> officerTimeSheetCustoms = createNewTimeSheetChangeYear(choiceBoxYear.getValue(), startMonth, endMonth);
                officerTimeSheetsList = FXCollections.observableArrayList(
                        officerTimeSheetCustoms
                );
                table.setItems(officerTimeSheetsList);
            }
        });
        table.setRowFactory(tv -> {
            TableRow<OfficerTimeSheetCustom> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (! row.isEmpty() && event.getButton()== MouseButton.PRIMARY
                        && event.getClickCount() == 2) {
                    OfficerTimeSheetCustom clickedRow = row.getItem();
                    RangeTimeView.setMonthStart((clickedRow.getMonthStart()));
                    RangeTimeView.setMonthEnd(clickedRow.getMonthEnd());
                    RangeTimeView.setYear(clickedRow.getYear());
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
