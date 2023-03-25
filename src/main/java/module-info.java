module com.example.projectjavafx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires java.sql;

    opens com.example.projectjavafx to javafx.fxml;
    exports com.example.projectjavafx;
    exports com.example.projectjavafx.database;
    opens com.example.projectjavafx.database to javafx.fxml;
    exports com.example.projectjavafx.menu;
    opens com.example.projectjavafx.menu to javafx.fxml;
    exports com.example.projectjavafx.ventanas;
    opens com.example.projectjavafx.ventanas to javafx.fxml;
}