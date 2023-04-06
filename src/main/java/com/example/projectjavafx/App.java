package com.example.projectjavafx;

import com.example.projectjavafx.database.Database;
import com.example.projectjavafx.database.DatabaseDialog;
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class App extends Application {

    private Stage stage;

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;

        DatabaseDialog databaseDialog = new DatabaseDialog();
        Optional<Database> result = databaseDialog.showAndWait();

        result.ifPresent(database -> {
            showLoginScene();
        });

        stage.setOnCloseRequest(event -> {
            event.consume();
            logOut(stage);
        });
    }

    protected void showLoginScene() {
        LoginScene loginScene = new LoginScene(stage, this);
        loginScene.show();
    }

    protected void showHomePage() throws IOException {
        HomePageScene homePageScene = new HomePageScene(stage);
        homePageScene.show();
    }

    public void logOut(Stage stage) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("LogOut");
        alert.setHeaderText("Estas a punto de salir!");

        if (alert.showAndWait().get() == ButtonType.OK) {
            System.out.println("Saliste exitosamente!");
            stage.close();
        }
    }

    public static void main(String[] args) {
        launch();
    }

}