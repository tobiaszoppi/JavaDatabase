package com.example.projectjavafx;

public class Usuario implements Comparable<Usuario> {
    public String username;
    //private String email;

    public Usuario(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public int compareTo(Usuario o) {
        return username.compareTo(o.username);
    }
}
