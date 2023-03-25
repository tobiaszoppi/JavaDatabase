package com.example.projectjavafx;

import com.example.projectjavafx.database.DatabaseService;

import java.sql.SQLException;

/*
La clase Session es una clase que se encarga de mantener el estado de la sesión de un usuario.
Contiene información sobre si la sesión está activa, el nombre de usuario que ha iniciado sesión,
si es un administrador y una instancia de la clase DatabaseService.
 */

public class Session {
    private static Session instance = null;
    private static App app;
    private boolean isActive;
    private String username;
    private boolean isAdmin;
    private DatabaseService db = new DatabaseService();

    public Session() {
        isActive = false;
        username = null;
        isAdmin = false;
    }

    public static Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }

    public void setApp(App app) {
        this.app = app;
    }
    public App getApp() {
        return app;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getUsername() {
        return username;
    }


    public boolean checkIsActive(String username) {
        try {
            isActive = db.isActive(username);
            return isActive;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean setUserActive(String username, boolean isActive) {
        try {
            if (db.setIsActive(username, isActive)) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isAdmin() {
        return isAdmin;
    }
    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public void logout() {
        app.showLoginScene();
        setUserActive(username, false);
        System.out.println("Adios");
    }
}
