package com.example.projectjavafx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class VentanaGestionarUsuariosController {
    // Atributos de clase
    private ObservableList<Usuario> usuarios;
    private Database db = new Database();

    // Metodos de clase para acceder a la lista de usuarios:

    public VentanaGestionarUsuariosController() throws SQLException {
        usuarios = FXCollections.observableArrayList();
        usuarios.setAll(obtenerUsuarios());
    }

    public ObservableList<Usuario> getUsuarios() {
        return usuarios;
    }

    private List<Usuario> obtenerUsuarios() throws SQLException {
        List<String> nombres = db.getAllNames();
        Usuario[] arrUsuarios = new Usuario[nombres.size()];
        int i = 0;
        for (String nombre : nombres) {
            arrUsuarios[i++] = new Usuario(nombre);
        }
        SelectionSort ss = new SelectionSort();
        ss.selectionSort(arrUsuarios, arrUsuarios.length);
        return Arrays.asList(arrUsuarios);
    }

    protected void deleteUsuario(Usuario usuario) throws SQLException {
        if(db.deleteUser(usuario.getUsername())) {
            usuarios.remove(usuario);
        } else {
            throw new SQLException("No se pudo eliminar el usuario: " + usuario.getUsername());
        }
    }
}