package com.example.projectjavafx.menu;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

import java.util.List;

public class MyMenuBar {
    private final MenuBar menuBar;

    public MyMenuBar(List<MenuInfo> menus) {

        menuBar = new MenuBar();

        for (MenuInfo menuInfo : menus) {

            Menu menu = new Menu(menuInfo.getName());

            for (String menuItemName : menuInfo.getMenuItems()) {

                MenuItem menuItem = new MenuItem(menuItemName);
                menu.getItems().add(menuItem);

            }

            menuBar.getMenus().add(menu);

        }
    }

    public void setEventHandler(MenuItemHandler eventHandler) {
        menuBar.getMenus().forEach(menu -> menu.getItems().forEach(item -> {
            item.setOnAction(eventHandler);
        }));
    }

    public MenuBar getMenuBar() {
        return menuBar;
    }
}
