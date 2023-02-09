package com.example.projectjavafx;

public class Usuario implements Comparable<Usuario> {
    public String username;
    public boolean isAdmin;

    public Usuario(String username) {
        this.username = username;
        this.isAdmin = Session.getInstance().isAdmin();
    }
    public Usuario(String username, Boolean isAdmin) {
        this.username = username;
        this.isAdmin = isAdmin;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public int compareTo(Usuario o) {
        return username.compareTo(o.username);
    }

    public boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
}
