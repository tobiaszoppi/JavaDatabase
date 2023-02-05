package com.example.projectjavafx;

public class Usuario implements Comparable<Usuario> {
    public String username;
    private Database db = new Database();

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
