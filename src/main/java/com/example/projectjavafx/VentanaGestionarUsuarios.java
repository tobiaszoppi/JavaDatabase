package com.example.projectjavafx;

import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;

import java.sql.SQLException;

public class VentanaGestionarUsuarios extends VentanaBase {
    private TableView<Usuario> tablaUsuarios;
    private VentanaGestionarUsuariosController controller;

    public VentanaGestionarUsuarios() throws SQLException {
        super("Gestionar Usuarios", Modality.WINDOW_MODAL);

        controller = new VentanaGestionarUsuariosController();

        tablaUsuarios = new TableView<>();
        TableColumn<Usuario, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(new PropertyValueFactory<>("username"));
        tablaUsuarios.getColumns().add(colNombre);
        // Agregar más columnas si es necesario

        // Asignar los datos de los usuarios a la tabla
        tablaUsuarios.setItems(controller.getUsuarios());

        // Agregar la tabla a la escena y mostrar la ventana
        Scene scene = new Scene(tablaUsuarios, 400, 300);
        window.setScene(scene);
        window.show();
    }
}
