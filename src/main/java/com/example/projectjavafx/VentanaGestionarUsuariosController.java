package com.example.projectjavafx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;

import java.net.URL;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class VentanaGestionarUsuariosController implements Initializable, Observer {
    // Atributos de clase
    protected ObservableList<Usuario> usuarios;
    private Session session = Session.getInstance();
    DatabaseService db = new DatabaseService();

    // Metodos de clase para acceder a la lista de usuarios:
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
            Boolean isAdmin = db.isAdmin(nombre);
            arrUsuarios[i++] = new Usuario(nombre, isAdmin);
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
            session.notifyObservers();
        } else {
            throw new SQLException("No se pudo eliminar el usuario: " + usuario.getUsername());
        }
    }

    protected void setAdmin(Usuario usuario) throws SQLException {
        if (db.setAdmin(usuario.getUsername())) {
            if (usuario.getIsAdmin()) {
                usuario.setIsAdmin(false);
            } else {
                usuario.setIsAdmin(true);
            }
            session.notifyObservers();
        } else {
            throw new SQLException("No se pudo eliminar el usuario: " + usuario.getUsername());
        }
    }

    @Override
    public void update() {
        try {
            updateTable();
        } catch (SQLException e) {
            System.out.println("No se pudo actualizar la tabla");
        }
    }
    public void updateTable() throws SQLException {
        usuarios.setAll(fetchUsers());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Session.getInstance().registerObserver(this);
    }
}