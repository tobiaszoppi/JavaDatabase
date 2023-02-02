package com.example.projectjavafx;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;

import java.util.HashMap;
import java.util.Map;

public class MenuItemHandler implements EventHandler<ActionEvent> {
    private Map<String, Runnable> actions;
    LoginController loginController = new LoginController();

    public MenuItemHandler() {
        actions = new HashMap<>();

        actions.put("Register", new Runnable(){

            @Override
            public void run() {
                new VentanaRegistroController();
            }
        });

        actions.put("Logout", new Runnable() {
            @Override
            public void run() {
                if (loginController.isloggedIn())
                    loginController.logout();
                    System.out.println("Logout");
            }
        });

        actions.put("Save", new Runnable() {
            @Override
            public void run() {
            }
        });

    }

    public void handle(ActionEvent event) {
        MenuItem item = (MenuItem) event.getSource();
        String text = item.getText();

        // Obtiene la acción asociada con el texto del menú seleccionado
        Runnable action = actions.get(text);
        if (action != null) {
            action.run();
        } else {
            // Código que se ejecutará por defecto para cualquier otro menú
            System.out.println("No se encontró la acción para el menú: " + text);
        }
    }
}
