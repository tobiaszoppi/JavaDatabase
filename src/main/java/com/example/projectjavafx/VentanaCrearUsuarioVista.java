package com.example.projectjavafx;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class VentanaCrearUsuarioVista extends VentanaRegistroVista {
    private Label label;
    private CheckBox checkBox1;
    private CheckBox checkBox2;
    private VentanaCrearUsuarioController controller;

    public VentanaCrearUsuarioVista(VentanaCrearUsuarioController controller) {
        super("Crear Usuario");
        this.controller = controller;
        adminView();

        // Agregamos el controlador a los botones
        super.registerBtn.setOnAction(e -> controller.handleRegistration());
        super.closeBtn.setOnAction(e -> controller.handleClose());
    }

    public void adminView() {
        if (Session.getInstance().isAdmin()) {
            label = new Label("Crear Usuario -permissionType:Admin");
            checkBox1 = new CheckBox("Opcion 1");
            checkBox2 = new CheckBox("Opcion 2");

            // Para controlar la posición de los nuevos objetos en
            // relación con los objetos existentes hago un nuevo layout
            HBox labelLayout = new HBox(label);
            labelLayout.setAlignment(Pos.CENTER);

            HBox checkBoxLayout = new HBox(checkBox1, checkBox2);
            checkBoxLayout.setAlignment(Pos.CENTER);
            checkBoxLayout.setSpacing(10);

            VBox layout = (VBox) super.window.getScene().getRoot();
            layout.getChildren().add(0, labelLayout);
            layout.getChildren().add(1, checkBoxLayout);
        }
    }

    public Button getCloseBtn() {
        return closeBtn;
    }
}