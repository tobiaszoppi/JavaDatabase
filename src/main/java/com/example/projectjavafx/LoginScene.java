package com.example.projectjavafx;

import com.example.projectjavafx.menu.MenuInfo;
import com.example.projectjavafx.menu.MyMenuBar;
import com.example.projectjavafx.ventanas.VentanaRegistroController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LoginScene {
    private final Stage stage;
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
        stage.setScene(scene);
        stage.show();
    }

    private void createMyMenuBar() {
        List<MenuInfo> menus = new ArrayList<>();
        menus.add(new MenuInfo("File", Arrays.asList("Register", "Logout")));
        menus.add(new MenuInfo("Help", Arrays.asList("About")));
        MyMenuBar myMenuBar = new MyMenuBar(menus);
        myMenuBar.setAction("Register", () -> {
            try {
                new VentanaRegistroController();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        myMenuBar.setAction("Logout", () -> {
            if (Session.getInstance().checkIsActive(Session.getInstance().getUsername())) {
                Session.getInstance().logout();
                System.out.println("Logout");
            } else System.out.println("No es posible cerrar sesion si no esta activo");
        });
        myMenuBar.setAction("About", () -> {
            System.out.println("About no implementado");
        });

        loginController.init(myMenuBar);
    }

}