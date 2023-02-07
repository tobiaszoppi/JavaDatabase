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
        menus.add(new MenuInfo("Help", Arrays.asList("About")));
        myMenuBar = new MyMenuBar(menus);
        MenuItemHandler menuItemHandler = new MenuItemHandler();
        myMenuBar.setEventHandler(menuItemHandler);
    }
}