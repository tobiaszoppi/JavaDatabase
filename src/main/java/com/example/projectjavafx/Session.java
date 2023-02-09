package com.example.projectjavafx;

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
    private DatabaseService db;

    public Session() {
        isActive = false;
        username = null;
        isAdmin = false;
        db = new DatabaseService();
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

    public boolean checkIsActive(String username) {
        try {
            return isActive = db.isActive(username);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean setUserActive(String username, boolean isActive) {
        try {
            return db.setIsActive(username, isActive);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getUsername() {
        return username;
    }

    protected void logout() {
        app.showLoginScene();
        // TODO: logout from server
        setUserActive(username, false);
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
}
