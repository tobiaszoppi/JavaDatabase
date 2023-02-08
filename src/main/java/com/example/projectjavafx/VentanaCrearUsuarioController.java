package com.example.projectjavafx;

import java.sql.SQLException;

public class VentanaCrearUsuarioController {
    private VentanaCrearUsuarioVista vista;
    private UserServices userServices;

    public VentanaCrearUsuarioController() throws SQLException {
        this.userServices = new UserServices();
        this.vista = new VentanaCrearUsuarioVista(this);
        show();
    }

    public void handleRegistration() {
        try {
            if (userServices.handleRegistration(vista.getUser().getText(), vista.getPass().getText())) {
                vista.close();
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void handleClose() {
        vista.close();
    }

    public void show() {
        vista.show();
    }
}