package com.example.projectjavafx;

public class Session {
    private static Session instance;
    private boolean isAdmin;
    private String username;

    private Session() {}
    public static Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
    public boolean isAdmin() {
        return isAdmin;
    }
    public String getUsername() {
        return username;
    }
}
