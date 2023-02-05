package com.example.projectjavafx;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class VentanaCrearUsuarioVista extends VentanaRegistroVista {
    private Label label;

    public VentanaCrearUsuarioVista(String crearUsuario) {
        super(crearUsuario);
        label = new Label("Ingrese el nombre y contraseña del usuario a crear");

        // Para controlar la posicion de los nuevos objetos en
        // relacion con los objetos existentes hago un nuevo layout
        HBox labelLayout = new HBox(label);
        labelLayout.setAlignment(Pos.CENTER);

        VBox layout = (VBox) super.window.getScene().getRoot();
        layout.getChildren().add(0, labelLayout);
    }
}
