package com.example.projectjavafx;

import java.sql.SQLException;

public class VentanaCrearUsuarioController {
    public VentanaCrearUsuarioController() {
        LoginController loginController = new LoginController();
        VentanaCrearUsuarioVista ventana = new VentanaCrearUsuarioVista("Crear Usuario");

        ventana.getRegisterBtn().setOnAction(e -> {
            try {
                if (loginController.handleRegistration(ventana.getUser().getText(), ventana.getPass().getText())) {
                    ventana.close();
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        ventana.getCloseBtn().setOnAction(e -> ventana.close());

        ventana.show();
    }
}