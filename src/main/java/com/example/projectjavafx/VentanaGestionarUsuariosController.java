package com.example.projectjavafx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class VentanaGestionarUsuariosController {
    // Atributos de clase
    private final ObservableList<Usuario> usuarios;
    Database db = Database.getInstance();

    // Metodos de clase para acceder a la lista de usuarios:
    public VentanaGestionarUsuariosController() throws SQLException {
        usuarios = FXCollections.observableArrayList();
        usuarios.setAll(fetchUsers());
    }

    public ObservableList<Usuario> getUsuarios() {
        return usuarios;
    }

    private List<Usuario> fetchUsers() throws SQLException {
        List<String> nombres = db.getAllNames();
        Usuario[] arrUsuarios = mapToUsuarioArray(nombres);
        sortUsuarios(arrUsuarios);
        return Arrays.asList(arrUsuarios);
    }

    private Usuario[] mapToUsuarioArray(List<String> nombres) {
        Usuario[] arrUsuarios = new Usuario[nombres.size()];
        int i = 0;
        for (String nombre : nombres) {
            arrUsuarios[i++] = new Usuario(nombre);
        }
        return arrUsuarios;
    }

    private void sortUsuarios(Usuario[] arrUsuarios) {
        SelectionSort ss = new SelectionSort();
        ss.selectionSort(arrUsuarios, arrUsuarios.length);
    }

    protected void deleteUsuario(Usuario usuario) throws SQLException {
        if (db.deleteUser(usuario.getUsername())) {
            usuarios.remove(usuario);
        } else {
            throw new SQLException("No se pudo eliminar el usuario: " + usuario.getUsername());
        }
    }
}