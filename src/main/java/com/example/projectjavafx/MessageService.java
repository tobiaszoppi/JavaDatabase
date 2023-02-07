package com.example.projectjavafx;

import javafx.scene.control.Alert;

public class MessageService {
    // Alertas
    public void showConfirmationAlert(String title, String header, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }
    public void showErrorAlert(String title, String header, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(header);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }
}

/*
showErrorAlert("Registro", "Error en el registro", "Usuario " + username + " no registrado");
showConfirmationAlert("Registro", "Registro exitoso", "Usuario " + username + " registrado correctamente, ya puede iniciar sesión!");
showErrorAlert("Registro", "Error en el registro", "Usuario " + username + " ya existe");
showErrorAlert("Error", "Error de registro", "Error de registro: No se ha introducido ningún usuario o contraseña");
db.showErrorAlert("Error", "Error de sesión", "Error de sesión: no se encuentra dentro de una sesión");
  db.showErrorAlert("Login", "Error en el login", "Usuario o contraseña incorrectos");
 */