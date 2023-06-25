module com.example.demo2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.demo2 to javafx.fxml;
    exports com.example.demo2;
    exports com.example.demo2.controller;
    opens com.example.demo2.controller to javafx.fxml;
    opens com.example.demo2.entity to javafx.fxml;
    exports com.example.demo2.entity;
}