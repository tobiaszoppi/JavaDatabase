package com.example.projectjavafx.ventanas;

import com.example.projectjavafx.Session;
import com.example.projectjavafx.database.DatabaseService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class VentanaGestionarUsuariosController {

    private ObservableList<Usuario> usuarios;
    private DatabaseService db = new DatabaseService();

    public VentanaGestionarUsuariosController() throws SQLException {
        usuarios = FXCollections.observableArrayList();
        usuarios.setAll(fetchUsers());
    }

    public ObservableList<Usuario> getUsuarios() {
        return usuarios;
    }

    protected List<Usuario> fetchUsers() throws SQLException {
        List<String> nombres = db.getAllNames();
        Usuario[] arrUsuarios = mapToUsuarioArray(nombres);
        sortUsuarios(arrUsuarios);
        return Arrays.asList(arrUsuarios);
    }

    private Usuario[] mapToUsuarioArray(List<String> nombres) throws SQLException {
        Usuario[] arrUsuarios = new Usuario[nombres.size()];
        int i = 0;
        for (String nombre : nombres) {
            arrUsuarios[i++] = new Usuario(nombre, db.isAdmin(nombre));
        }
        return arrUsuarios;
    }

    private void sortUsuarios(Usuario[] arrUsuarios) {
        SelectionSort ss = new SelectionSort();
        ss.selectionSort(arrUsuarios, arrUsuarios.length);
    }

    protected void deleteUsuario(Usuario usuario, VentanaGestionarUsuariosVista vista) throws SQLException {
        if (Session.getInstance().checkIsActive(usuario.getUsername())) {
            vista.close();
            Session.getInstance().logout();
        }
        if (db.deleteUser(usuario.getUsername())) {
            usuarios.remove(usuario);
        } else {
            throw new SQLException("No se pudo eliminar el usuario: " + usuario.getUsername());
        }
    }

    protected void setAdmin(Usuario usuario) throws SQLException {
        if (db.setAdmin(usuario.getUsername())) {
            usuario.setIsAdmin(!usuario.getIsAdmin());
        } else {
            throw new SQLException("No se pudo establecer el usuario como administrador: " + usuario.getUsername());
        }
    }
}