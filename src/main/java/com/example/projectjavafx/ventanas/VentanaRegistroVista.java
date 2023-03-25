package com.example.projectjavafx.ventanas;


import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;

public class VentanaRegistroVista extends VentanaBase {

    private Label label;
    private TextField user;
    private PasswordField pass;
    protected Button registerBtn;
    protected Button closeBtn;

    public VentanaRegistroVista(String crearUsuario) {
        super(crearUsuario, Modality.APPLICATION_MODAL);
        initComponents();
        addComponentsToLayout();
    }

    private void initComponents() {
        label = new Label("Ingresar Nombre de Usuario y Contraseña");
        user = new TextField();
        pass = new PasswordField();
        registerBtn = new Button("Registrar");
        closeBtn = new Button("Cerrar");
    }

    private void addComponentsToLayout() {
        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, user, pass, registerBtn, closeBtn);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout, 300, 250);
        window.setScene(scene);
    }

    public TextField getUser() {
        return user;
    }

    public PasswordField getPass() {
        return pass;
    }

    public Button getRegisterBtn() {
        return registerBtn;
    }

    public Button getCloseBtn() {
        return closeBtn;
    }
}