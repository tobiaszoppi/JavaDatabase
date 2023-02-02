package com.example.projectjavafx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VentanaGestionarUsuariosController {
    private ObservableList<Usuario> usuarios;
    private Database db = new Database();

    public VentanaGestionarUsuariosController() throws SQLException {
        usuarios = FXCollections.observableArrayList();
        usuarios.setAll(obtenerUsuarios());
    }

    public ObservableList<Usuario> getUsuarios() {
        return usuarios;
    }

    private List<Usuario> obtenerUsuarios() throws SQLException {
        List<String> nombres = db.getAllNames();
        List<Usuario> usuarios = new ArrayList<>();
        for (String nombre : nombres) {
            usuarios.add(new Usuario(nombre));
        }
        return usuarios;
    }
}

