package com.example.projectjavafx;

import java.sql.SQLException;

public class Usuario implements Comparable<Usuario> {
    public String username;
    Database db;
    {
        try {
            db = Database.getInstance();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Usuario(String username) {
        this.username = username;
        boolean isAdmin = db.isAdmin(username); // en algún momento tendre que configurarlo mejor...
    }

    public String getUsername() {
        return username;
    }

    @Override
    public int compareTo(Usuario o) {
        return username.compareTo(o.username);
    }

}
