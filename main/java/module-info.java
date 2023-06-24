module com.example.itss {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.itss to javafx.fxml;
    exports com.example.itss;
}