package com.example.projectjavafx;

import com.example.projectjavafx.menu.MenuInfo;
import com.example.projectjavafx.menu.MyMenuBar;
import com.example.projectjavafx.ventanas.VentanaCrearUsuarioController;
import com.example.projectjavafx.ventanas.VentanaGestionarUsuariosVista;
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

public class HomePageScene {
    private final Stage stage;

    public HomePageScene(Stage stage) {
        this.stage = stage;
        loadScene();

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

        if (alert.showAndWait().get() == ButtonType.OK) {
            Session.getInstance().logout();
            System.out.println("Saliste exitosamente!");
            stage.close();
        }
    }

    private void loadScene() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("homeScen.fxml"));

        try {
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            addMyMenuBarToScene(scene, fxmlLoader);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addMyMenuBarToScene(Scene scene, FXMLLoader fxmlLoader) {
        List<MenuInfo> menus = new ArrayList<>();
        menus.add(new MenuInfo("File", Arrays.asList("Logout", "Open")));
        menus.add(new MenuInfo("Usuario", Arrays.asList("Nuevo Usuario", "Gestionar Usuarios")));

        MyMenuBar myMenuBar = new MyMenuBar(menus);

        // Define acciones para las opciones de menú
        myMenuBar.setAction("Logout", () -> {
            if (Session.getInstance().checkIsActive(Session.getInstance().getUsername())) {
                Session.getInstance().logout();
                System.out.println("Logout");
            } else System.out.println("No es posible cerrar sesion si no esta activo");
        });
        myMenuBar.setAction("Open", () -> {
            System.out.println("Open no implementado");
        });
        myMenuBar.setAction("Nuevo Usuario", () -> {
            try {
                new VentanaCrearUsuarioController();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        myMenuBar.setAction("Gestionar Usuarios", () -> {
            try {
                new VentanaGestionarUsuariosVista();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        HomePageController homePageController = fxmlLoader.getController();
        homePageController.init(myMenuBar);
    }

    public void show() {
        stage.show();
    }
}