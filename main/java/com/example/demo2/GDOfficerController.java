package com.example.demo2;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

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

    private  String[] month = {"tháng 1","tháng 2", "tháng 3", "tháng 4", "tháng 5", "tháng 6", "tháng 7", "tháng 8", "tháng 9", "tháng 10", "tháng 11", "tháng 12"};
    private  String[] year = {"tháng 1","tháng 2", "tháng 3", "tháng 4", "tháng 5", "tháng 6", "tháng 7", "tháng 8", "tháng 9", "tháng 10", "tháng 11", "tháng 12"};
    private boolean isOpen = true;

    private ObservableList<OfficerTimeSheet> officerTimeSheetsList;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        closeApp.setOnMouseClicked(event -> {
            System.exit(0);
        });
        officerTimeSheetsList = FXCollections.observableArrayList(
                new OfficerTimeSheet("21/06/2023", "có", "có", 0.5,0.25),
                new OfficerTimeSheet("21/06/2023", "có", "có", 0.5,0.25),
                new OfficerTimeSheet("21/06/2023", "có", "có", 0.5,0.25),
                new OfficerTimeSheet("21/06/2023", "có", "có", 0.5,0.25),
                new OfficerTimeSheet("21/06/2023", "có", "có", 0.5,0.25),
                new OfficerTimeSheet("21/06/2023", "có", "có", 0.5,0.25),
                new OfficerTimeSheet("21/06/2023", "có", "có", 0.5,0.25)
        );

        dateCol.setCellValueFactory(new PropertyValueFactory<OfficerTimeSheet, String>("date"));
        morningCol.setCellValueFactory(new PropertyValueFactory<OfficerTimeSheet, String>("morning"));
        afternoonCol.setCellValueFactory(new PropertyValueFactory<OfficerTimeSheet, String>("afternoon"));
        lateHoursCol.setCellValueFactory(new PropertyValueFactory<OfficerTimeSheet, String>("lateHours"));
        soonHoursCol.setCellValueFactory(new PropertyValueFactory<OfficerTimeSheet, String>("soonHours"));

        table.setItems(officerTimeSheetsList);
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
}
