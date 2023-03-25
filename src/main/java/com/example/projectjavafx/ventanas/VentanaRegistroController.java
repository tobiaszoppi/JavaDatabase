package com.example.projectjavafx.ventanas;

import com.example.projectjavafx.UserServices;

import java.sql.SQLException;

public class VentanaRegistroController {
    public VentanaRegistroController() throws SQLException {
        UserServices userServices = new UserServices();
        VentanaRegistroVista ventana = new VentanaRegistroVista("Crear Usuario");

        addEventHandlers(ventana, userServices);
        ventana.show();
    }

    private void addEventHandlers(VentanaRegistroVista ventana, UserServices userServices) {
        ventana.getRegisterBtn().setOnAction(e -> handleRegistration(ventana, userServices));
        ventana.getCloseBtn().setOnAction(e -> ventana.close());
    }

    private void handleRegistration(VentanaRegistroVista ventana, UserServices userServices) {
        try {
            if (userServices.handleRegistration(ventana.getUser().getText(), ventana.getPass().getText())) {
                ventana.close();
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}