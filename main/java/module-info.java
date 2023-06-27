module com.example.demo2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;

//    opens com.example.demo2.entity to com.google.gson;
    opens com.example.demo2 to javafx.fxml;
    exports com.example.demo2;
    opens com.example.demo2.entity to javafx.fxml, com.google.gson;
    exports com.example.demo2.entity;
    exports com.example.demo2.officer;
    opens com.example.demo2.officer to javafx.fxml;
}