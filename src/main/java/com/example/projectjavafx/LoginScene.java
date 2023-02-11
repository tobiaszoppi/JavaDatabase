package com.example.projectjavafx;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LoginScene {
    private final Stage stage;
    private MyMenuBar myMenuBar;
    private LoginController loginController;
    private Scene scene;

    public LoginScene(Stage stage, App app) {
        this.stage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("loginScene.fxml"));

        try {
            Parent root = fxmlLoader.load();
            scene = new Scene(root);
            loginController = fxmlLoader.getController();
            Session.getInstance().setApp(app);
        } catch (IOException e) {
            e.printStackTrace();
        }

        stage.setOnCloseRequest(event -> {
            event.consume();
            logOut(stage);
        });
    }

    public void logOut(Stage stage) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("LogOut");
        alert.setHeaderText("Estas a punto de salir!");
        //alert.setContentText("Desea guardar antes de salir?: ");

        if(alert.showAndWait().get() == ButtonType.OK) {
            System.out.println("Saliste exitosamente!");
            stage.close();
        }
    }

    public void show() {
        createMyMenuBar();
        loginController.init(myMenuBar);
        stage.setScene(scene);
        stage.show();
    }

    private void createMyMenuBar() {
        List<MenuInfo> menus = new ArrayList<>();
        menus.add(new MenuInfo("File", Arrays.asList("Register", "Logout")));
        menus.add(new MenuInfo("Help", List.of("About")));
        myMenuBar = new MyMenuBar(menus);
        MenuItemHandler menuItemHandler = new MenuItemHandler();
        myMenuBar.setEventHandler(menuItemHandler);
    }
}