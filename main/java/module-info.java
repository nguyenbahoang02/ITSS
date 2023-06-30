module com.example.demo2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;

//    opens com.example.demo2.entity to com.google.gson;
    opens com.example.demo2 to javafx.fxml;
    exports com.example.demo2;
    opens com.example.demo2.Model to javafx.fxml, com.google.gson;
    exports com.example.demo2.Model;
    exports com.example.demo2.officer;
    opens com.example.demo2.officer to javafx.fxml;
    opens com.example.demo2.unitLeader to javafx.fxml;
    opens com.example.demo2.manager to javafx.fxml;
    exports com.example.demo2.Model.Officer;
    opens com.example.demo2.Model.Officer to com.google.gson, javafx.fxml;
    exports com.example.demo2.Model.Worker;
    opens com.example.demo2.Model.Worker to com.google.gson, javafx.fxml;
    exports com.example.demo2.worker;
    opens com.example.demo2.worker to com.google.gson, javafx.fxml;
}