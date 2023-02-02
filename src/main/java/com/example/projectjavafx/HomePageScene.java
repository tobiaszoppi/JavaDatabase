package com.example.projectjavafx;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomePageScene {
    private Stage stage;

    public HomePageScene(Stage stage) {
        this.stage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("homeScen.fxml"));

        try {
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);

                // Agregar el primer MyMenuBar a la escena
                List<MenuInfo> menus = new ArrayList<>();
                menus.add(new MenuInfo("File", Arrays.asList("Logout", "Open")));
                MyMenuBar myMenuBar = new MyMenuBar(menus);
                MenuItemHandler menuItemHandler = new MenuItemHandler();

                myMenuBar.setEventHandler(menuItemHandler);

                // Agregar el segundo MyMenuBar a la escena
                List<MenuInfo> menus2 = new ArrayList<>();
                menus2.add(new MenuInfo("Usuario", Arrays.asList("Nuevo Usuario", "Gestionar Usuarios")));
                MyMenuBar myMenuBar2 = new MyMenuBar(menus2);
                MenuItemHandler menuItemHandler2 = new MenuItemHandler();

                myMenuBar2.setEventHandler(menuItemHandler2);

            HomePageController homePageController = fxmlLoader.getController();
            homePageController.init(myMenuBar, myMenuBar2);

            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void show() {stage.show();}
}
