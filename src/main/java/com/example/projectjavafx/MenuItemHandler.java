package com.example.projectjavafx;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class MenuItemHandler implements EventHandler<ActionEvent> {
    private final Map<String, Runnable> actions;

    public MenuItemHandler() {
        actions = new HashMap<>();

        actions.put("Register", new Runnable() {
            @Override
            public void run() {
                try {
                    new VentanaRegistroController();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        actions.put("Logout", new Runnable() {
            @Override
            public void run() {
                if (Session.getInstance().isLoggedIn())
                    Session.getInstance().logout();
                System.out.println("Logout");
            }
        });
        actions.put("Nuevo Usuario", new Runnable() {
            @Override
            public void run() {
                try {
                    new VentanaCrearUsuarioController();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        actions.put("Gestionar Usuarios", new Runnable() {
            @Override
            public void run() {
                try {
                    new VentanaGestionarUsuariosVista();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
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
