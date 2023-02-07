package com.example.projectjavafx;

import java.sql.SQLException;

public class VentanaCrearUsuarioController {
    public VentanaCrearUsuarioController() throws SQLException {
        UserServices userServices = new UserServices();
        VentanaCrearUsuarioVista ventana = new VentanaCrearUsuarioVista("Crear Usuario");

        ventana.getRegisterBtn().setOnAction(e -> {
            try {
                if (userServices.handleRegistration(ventana.getUser().getText(), ventana.getPass().getText())) {
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