package com.example.projectjavafx;

import com.example.projectjavafx.menu.MenuInfo;
import com.example.projectjavafx.menu.MyMenuBar;
import com.example.projectjavafx.proyectos.VentanaAlgoritmo;
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
import java.util.Optional;

public class HomePageScene {
    private final Stage stage;

    public HomePageScene(Stage stage) throws IOException {
        this.stage = stage;
        loadScene();

        stage.setOnCloseRequest(event -> {
            event.consume();
            logOut();
        });
    }

    private void logOut() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("LogOut");
        alert.setHeaderText("Estas a punto de salir!");
        //alert.setContentText("Desea guardar antes de salir?: ");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Session session = Session.getInstance();
            if (session.checkIsActive(session.getUsername())) {
                session.logout();
                System.out.println("Saliste exitosamente!");
                stage.close();
            } else {
                System.out.println("No es posible cerrar sesion si no esta activo");
            }
        }
    }

    private void loadScene() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("homeScen.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        addMyMenuBarToScene(fxmlLoader);
        stage.setScene(scene);
    }

    private void addMyMenuBarToScene(FXMLLoader fxmlLoader) {
        List<MenuInfo> menus = new ArrayList<>();
        menus.add(new MenuInfo("File", Arrays.asList("Logout", "Open")));
        menus.add(new MenuInfo("Usuario", Arrays.asList("Nuevo Usuario", "Gestionar Usuarios")));
        menus.add(new MenuInfo("Proyectos", Arrays.asList("Nuevo Proyecto", "Gestionar Proyectos", "Islas")));

        MyMenuBar myMenuBar = new MyMenuBar(menus);

        Session session = Session.getInstance();
        myMenuBar.setAction("Logout", () -> {
            if (session.checkIsActive(session.getUsername())) {
                session.logout();
                System.out.println("Logout");
            } else {
                System.out.println("No es posible cerrar sesion si no esta activo");
            }
        });
        myMenuBar.setAction("Open", System.out::println);
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
        myMenuBar.setAction("Nuevo Proyecto", System.out::println);
        myMenuBar.setAction("Gestionar Proyectos",System.out::println);
        myMenuBar.setAction("Islas", () -> {
            VentanaAlgoritmo ventanaAlgoritmo = new VentanaAlgoritmo("Algoritmo de islas cerradas");
            ventanaAlgoritmo.show();
        });

        HomePageController homePageController = fxmlLoader.getController();
        homePageController.init(myMenuBar);
    }

    public void show() {
        stage.show();
    }
}