package com.example.projectjavafx;

import java.util.List;

class MenuInfo {
    private String name;
    private List<String> menuItems;

    public MenuInfo(String name, List<String> menuItems) {
        this.name = name;
        this.menuItems = menuItems;
    }

    public String getName() {
        return name;
    }

    public List<String> getMenuItems() {
        return menuItems;
    }
}