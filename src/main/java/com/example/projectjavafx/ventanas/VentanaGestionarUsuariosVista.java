package com.example.projectjavafx.ventanas;

import com.example.projectjavafx.Session;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;

import java.sql.SQLException;

public class VentanaGestionarUsuariosVista extends VentanaBase {
    private TableView<Usuario> tablaUsuarios;
    private VentanaGestionarUsuariosController controller;

    public VentanaGestionarUsuariosVista() throws SQLException {
        super("Gestionar Usuarios", Modality.APPLICATION_MODAL);

        controller = new VentanaGestionarUsuariosController();
        inicializarTabla();
        agregarDatosATabla();
        agregarAccionesDeAdministrador();
        mostrarVentana();
    }

    private void inicializarTabla() {
        tablaUsuarios = new TableView<>();
        TableColumn<Usuario, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(new PropertyValueFactory<>("username"));
        tablaUsuarios.getColumns().add(colNombre);
    }

    private void agregarDatosATabla() {
        tablaUsuarios.setItems(controller.getUsuarios());
    }

    private void agregarAccionesDeAdministrador() {
        if (!Session.getInstance().isAdmin()) {
            return;
        }
        agregarBotonEliminar();
        agregarInfoAdmin();
    }

    private void agregarBotonEliminar() {
        TableColumn<Usuario, Button> colBoton = new TableColumn<>("Delete");
        tablaUsuarios.getColumns().add(colBoton);
        colBoton.setCellFactory(param -> new TableCell<>() {
            private final Button deleteButton = new Button("Delete");

            @Override
            protected void updateItem(Button item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                    return;
                }
                setGraphic(deleteButton);
                deleteButton.setOnAction(event -> {
                    Usuario usuario = getTableView().getItems().get(getIndex());
                    try {
                        controller.deleteUsuario(usuario, VentanaGestionarUsuariosVista.this);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });
            }
        });
    }

    private void agregarInfoAdmin() {
        TableColumn<Usuario, Button> colBoton = new TableColumn<>("Change User Type");
        TableColumn<Usuario, String> colInfo = new TableColumn<>("User Type");
        tablaUsuarios.getColumns().add(colBoton);
        tablaUsuarios.getColumns().add(colInfo);

        colInfo.setCellValueFactory(new PropertyValueFactory<>("isAdmin"));
        colBoton.setCellFactory(param -> new TableCell<>() {
            private final Button adminButton = new Button("Admin");

            @Override
            protected void updateItem(Button item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                    return;
                }
                setGraphic(adminButton);
                adminButton.setOnAction(event -> {
                    Usuario usuario = getTableView().getItems().get(getIndex());
                    try {
                        controller.setAdmin(usuario);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });
            }
        });
    }

    public void mostrarVentana() {
        Scene escena = new Scene(tablaUsuarios, 400, 300);
        window.setScene(escena);
        window.show();
    }
}