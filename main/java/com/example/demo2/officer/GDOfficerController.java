package com.example.demo2.officer;

import com.example.demo2.configure.RangeTimeView;
import com.example.demo2.entity.OfficerTimeSheet;
import com.example.demo2.entity.OfficerTimeSheetQuarter;
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
import java.io.*;
import java.nio.file.*;

public class GDOfficerController implements Initializable {
    @FXML
    private TableView<OfficerTimeSheet> table;
    @FXML
    private TableColumn<OfficerTimeSheet, String> dateCol;
    @FXML
    private TableColumn<OfficerTimeSheet, String> morningCol;
    @FXML
    private TableColumn<OfficerTimeSheet, String> afternoonCol;
    @FXML
    private TableColumn<OfficerTimeSheet, String> lateHoursCol;
    @FXML
    private TableColumn<OfficerTimeSheet, String> soonHoursCol;
    @FXML
    private ChoiceBox<String> choiceBoxMonth;
    @FXML
    private ChoiceBox<String> choiceBoxYear;
    @FXML
    private Label closeApp;
    @FXML
    private ImageView menuIcon;
    @FXML
    private ImageView menuIcon2;
    @FXML
    private AnchorPane sidebar;
    @FXML
    private Polygon leftTriangle;
    @FXML
    private Polygon rightTriangle;
    // pageClone
    @FXML private AnchorPane clonePage;
    @FXML private Label dateLabelPopup;
    @FXML private Label startTimeLabel;
    @FXML private Label endTimeLabel;
    @FXML private Label lateHoursLabel;
    @FXML private Label soonHoursLabel;
    @FXML private AnchorPane popup;
    @FXML private Label closePopup;

    private Stage stage;
    private Scene scene;
    private  String[] month = {"tháng 1","tháng 2", "tháng 3", "tháng 4", "tháng 5", "tháng 6", "tháng 7", "tháng 8", "tháng 9", "tháng 10", "tháng 11", "tháng 12"};
    private  String[] year = {"2020", "2021", "2022", "2023"};
    private int page = 1;
    private boolean isOpen = true;

    private ObservableList<OfficerTimeSheet> officerTimeSheets;

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
        List<OfficerTimeSheet> officerTimeSheetOfRangeTime = new ArrayList<>();
        for (OfficerTimeSheet oneDay : officerTimeSheetList) {
            Date dateParse= null;
            try {
                dateParse = new SimpleDateFormat("dd/MM/yyyy").parse(oneDay.getDate());
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            if ((dateParse.getYear() + 1900) == RangeTimeView.getYear() && (dateParse.getMonth() + 1) >= RangeTimeView.getMonthStart() && (dateParse.getMonth() + 1) <= RangeTimeView.getMonthEnd()){
                officerTimeSheetOfRangeTime.add(oneDay);
            }
        }
        officerTimeSheets = FXCollections.observableArrayList(
                officerTimeSheetOfRangeTime
        );

        dateCol.setCellValueFactory(new PropertyValueFactory<OfficerTimeSheet, String>("date"));
        morningCol.setCellValueFactory(new PropertyValueFactory<OfficerTimeSheet, String>("morning"));
        afternoonCol.setCellValueFactory(new PropertyValueFactory<OfficerTimeSheet, String>("afternoon"));
        lateHoursCol.setCellValueFactory(new PropertyValueFactory<OfficerTimeSheet, String>("lateHours"));
        soonHoursCol.setCellValueFactory(new PropertyValueFactory<OfficerTimeSheet, String>("soonHours"));

        table.setItems(officerTimeSheets);
        choiceBoxMonth.getItems().addAll(month);
        choiceBoxYear.getItems().addAll(year);

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
        rightTriangle.setOnMouseClicked(event -> {
            List<OfficerTimeSheet> officerTimeSheetListNew = new ArrayList<>();
            for (int i = 0; i<7; i++){
                if( (page * 7 + i) < officerTimeSheetOfRangeTime.size() && officerTimeSheetOfRangeTime.get(page * 7 + i) != null)
                    officerTimeSheetListNew.add(officerTimeSheetOfRangeTime.get(page*7 + i));
            }
            officerTimeSheets = FXCollections.observableArrayList(officerTimeSheetListNew);
            if(officerTimeSheets.size() > 0){
                table.setItems(officerTimeSheets);
                page++;
            }
        });
        leftTriangle.setOnMouseClicked(event -> {
            if (page > 1){
                List<OfficerTimeSheet> officerTimeSheetListNew = new ArrayList<>();
                for (int i = 0; i<7; i++){
                    if( ( (page-2) * 7 + i >= 0 ) && officerTimeSheetOfRangeTime.get( (page - 2) * 7 + i) != null)
                        officerTimeSheetListNew.add(officerTimeSheetOfRangeTime.get( (page - 2)*7 + i));
                    else return;
                }
                officerTimeSheets = FXCollections.observableArrayList(officerTimeSheetListNew);
                if(officerTimeSheets.size() > 0){
                    table.setItems(officerTimeSheets);
                    page--;
                }
            }
        });

        table.setRowFactory(tv -> {
            TableRow<OfficerTimeSheet> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (! row.isEmpty() && event.getButton()== MouseButton.PRIMARY
                        && event.getClickCount() == 2) {
                    OfficerTimeSheet clickedRow = row.getItem();
                    for (OfficerTimeSheet oneDay : officerTimeSheetList) {
                        if (oneDay.getDate().equals(clickedRow.getDate())){
                            dateLabelPopup.setText(oneDay.getDate());
                            startTimeLabel.setText(oneDay.getStartTime());
                            endTimeLabel.setText(oneDay.getEndTime());
                            lateHoursLabel.setText(""+oneDay.getLateHours());
                            soonHoursLabel.setText("" + oneDay.getSoonHours());

                            clonePage.setVisible(true);
                            FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.5),clonePage);
                            fadeTransition.setFromValue(0);
                            fadeTransition.setToValue(0.15);
                            fadeTransition.play();
                            popup.setVisible(true);
                            FadeTransition fadeTransition1 = new FadeTransition(Duration.seconds(0.5),popup);
                            fadeTransition1.setFromValue(0);
                            fadeTransition1.setToValue(1);
                            fadeTransition1.play();


                        }
                    }
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
        TranslateTransition translateTransitionMonth = new TranslateTransition(Duration.seconds(0.5), choiceBoxMonth);
        TranslateTransition translateTransitionYear = new TranslateTransition(Duration.seconds(0.5), choiceBoxYear);
        TranslateTransition translateTransitionLeftTriangle = new TranslateTransition(Duration.seconds(0.5), leftTriangle);
        TranslateTransition translateTransitionRightTriangle = new TranslateTransition(Duration.seconds(0.5), rightTriangle);

        translateTransition.setByX(translateXSidebar);
        translateTransitionTable.setByX(translateXItem);
        translateTransitionMonth.setByX(translateXItem);
        translateTransitionYear.setByX(translateXItem);
        translateTransitionLeftTriangle.setByX(translateXItem);
        translateTransitionRightTriangle.setByX(translateXItem);
        table.setPrefWidth(table.getPrefWidth() - translateXItem);

        dateCol.setPrefWidth(dateCol.getPrefWidth() + tableColumnWidth);
        morningCol.setPrefWidth(morningCol.getPrefWidth() + tableColumnWidth);
        afternoonCol.setPrefWidth(afternoonCol.getPrefWidth() + tableColumnWidth);
        lateHoursCol.setPrefWidth(lateHoursCol.getPrefWidth() + tableColumnWidth);
        soonHoursCol.setPrefWidth(soonHoursCol.getPrefWidth() + tableColumnWidth);


        translateTransition.play();
        translateTransitionTable.play();
        translateTransitionMonth.play();
        translateTransitionYear.play();
        translateTransitionLeftTriangle.play();
        translateTransitionRightTriangle.play();

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
}
