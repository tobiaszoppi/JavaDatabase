package com.example.projectjavafx;

import java.sql.SQLException;

public class VentanaRegistroController {
    public VentanaRegistroController() {
        LoginController loginController = new LoginController();
        VentanaRegistroVista ventana = new VentanaRegistroVista("Crear Usuario");

        addEventHandlers(ventana, loginController);
        ventana.show();
    }

    private void addEventHandlers(VentanaRegistroVista ventana, LoginController loginController) {
        ventana.getRegisterBtn().setOnAction(e -> handleRegistration(ventana, loginController));
        ventana.getCloseBtn().setOnAction(e -> ventana.close());
    }

    private void handleRegistration(VentanaRegistroVista ventana, LoginController loginController) {
        try {
            if (loginController.handleRegistration(ventana.getUser().getText(), ventana.getPass().getText())) {
                ventana.close();
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}