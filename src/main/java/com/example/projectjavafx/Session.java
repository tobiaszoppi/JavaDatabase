package com.example.projectjavafx;

public class Session {
    private static Session instance = null;
    private static App app;
    private boolean isLoggedIn;
    private String username;
    private boolean isAdmin;

    public Session() {
        isLoggedIn = false;
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

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setIsLoggedIn(boolean isLoggedIn) {
        this.isLoggedIn = isLoggedIn;
    }

    public String getUsername() {
        return username;
    }

    protected void logout() {
        app.showLoginScene();
        // TODO: logout from server
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
