package com.example.projectjavafx;


import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;

public class VentanaRegistro extends VentanaBase {

    private Label label;
    private TextField user;
    private PasswordField pass;
    private Button registerBtn;
    private Button closeBtn;

    public VentanaRegistro() {
        super("Registrar usuario", Modality.APPLICATION_MODAL);

        label = new Label("Ingresar Nombre de Usuario y Contraseña");
        user = new TextField();
        pass = new PasswordField();
        registerBtn = new Button("Registrar");
        closeBtn = new Button("Cerrar");

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