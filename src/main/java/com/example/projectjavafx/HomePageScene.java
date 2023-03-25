package com.example.projectjavafx;

import com.example.projectjavafx.menu.MenuInfo;
import com.example.projectjavafx.menu.MenuItemHandler;
import com.example.projectjavafx.menu.MyMenuBar;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;
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
        List<MenuInfo> menus1 = List.of(new MenuInfo("File", Arrays.asList("Logout", "Open")));
        MyMenuBar myMenuBar1 = new MyMenuBar(menus1);
        myMenuBar1.setEventHandler(new MenuItemHandler());

        List<MenuInfo> menus2 = List.of(new MenuInfo("Usuario", Arrays.asList("Nuevo Usuario", "Gestionar Usuarios")));
        MyMenuBar myMenuBar2 = new MyMenuBar(menus2);
        myMenuBar2.setEventHandler(new MenuItemHandler());

        HomePageController homePageController = fxmlLoader.getController();
        homePageController.init(myMenuBar1, myMenuBar2);
    }

    public void show() {
        stage.show();
    }
}