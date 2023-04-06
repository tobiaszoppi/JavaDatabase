package com.example.projectjavafx.menu;

import com.example.projectjavafx.Session;
import com.example.projectjavafx.ventanas.VentanaCrearUsuarioController;
import com.example.projectjavafx.ventanas.VentanaGestionarUsuariosVista;
import com.example.projectjavafx.ventanas.VentanaRegistroController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyMenuBar extends MenuBar implements EventHandler<ActionEvent> {
    private final Map<String, Runnable> actions = new HashMap<>();

    public MyMenuBar(List<MenuInfo> menus) {
        for (MenuInfo menuInfo : menus) {
            Menu menu = new Menu(menuInfo.getName());
            for (String menuItemName : menuInfo.getMenuItems()) {
                MenuItem menuItem = new MenuItem(menuItemName);
                menuItem.setOnAction(this); // Agrega el evento de acción en lugar de usar una clase separada
                menu.getItems().add(menuItem);
            }
            this.getMenus().add(menu);
        }
    }

    public void setAction(String name, Runnable action) {
        actions.put(name, action);
    }

    @Override
    public void handle(ActionEvent event) {
        MenuItem menuItem = (MenuItem) event.getSource();
        Runnable action = actions.get(menuItem.getText());
        if (action != null) {
            action.run();
        }
    }
    /*
        La clase MyMenuBar ahora extiende la clase MenuBar y también implementa la interfaz EventHandler<ActionEvent>.
        La clase MenuItemHandler ya no es necesaria y se ha eliminado.
        Los elementos de menú ahora agregan el controlador de eventos de menú directamente en el bucle for que construye el menú.
        La clase MyMenuBar ahora tiene un HashMap de acciones que asocia las cadenas de elementos de menú con objetos de tipo Runnable que realizan tareas específicas.
        La clase MyMenuBar tiene un nuevo método setAction que permite agregar nuevas acciones al HashMap en tiempo de ejecución.
        El método handle de la clase MyMenuBar maneja todos los eventos de menú. Cuando se selecciona un elemento de menú, verifica si hay una acción asociada en el HashMap y la ejecuta si es así.
     */
}