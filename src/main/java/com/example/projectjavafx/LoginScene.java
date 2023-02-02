package com.example.projectjavafx;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LoginScene {
    private Stage stage;
    public LoginScene(Stage stage, App app) {
        this.stage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("loginScene.fxml"));

        try {
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);

                // Agregar MyMenuBar a la escena
                List<MenuInfo> menus = new ArrayList<>();
                menus.add(new MenuInfo("File", Arrays.asList("Register", "Logout")));
                menus.add(new MenuInfo("Help", Arrays.asList("About")));
                MyMenuBar myMenuBar = new MyMenuBar(menus);
                MenuItemHandler menuItemHandler = new MenuItemHandler();

                myMenuBar.setEventHandler(menuItemHandler);

            LoginController loginController = fxmlLoader.getController();
            loginController.setApp(app);
            loginController.init(myMenuBar);

            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void show() {
        stage.show();
    }
}
