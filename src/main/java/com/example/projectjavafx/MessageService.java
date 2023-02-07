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
messageService.showErrorAlert("Error", "Error de sesión", "Error de sesión: no se encuentra dentro de una sesión");
 */