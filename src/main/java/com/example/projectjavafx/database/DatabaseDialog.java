package com.example.projectjavafx.database;

import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.sql.SQLException;
import java.util.Optional;

public class DatabaseDialog extends Dialog<Database> {
    private final TextField databaseNameField = new TextField();
    private final TextField usernameField = new TextField();
    private final PasswordField passwordField = new PasswordField();
    private final CheckBox rememberCheckbox = new CheckBox("Recordar esta información");

    public DatabaseDialog() {
        setTitle("Conexión a la base de datos");
        setHeaderText("Ingrese los datos para conectarse a la base de datos");

        // Crear layout para la ventana y agregar campos
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.addRow(0, new Label("Nombre de la base de datos:"), databaseNameField);
        gridPane.addRow(1, new Label("Usuario:"), usernameField);
        gridPane.addRow(2, new Label("Contraseña:"), passwordField);
        gridPane.add(rememberCheckbox, 0, 3, 2, 1);

        getDialogPane().setContent(gridPane);

        // Agregar botones de aceptar y cancelar
        ButtonType connectButtonType = new ButtonType("Conectar", ButtonBar.ButtonData.OK_DONE);
        getDialogPane().getButtonTypes().addAll(connectButtonType, ButtonType.CANCEL);

        setResultConverter(dialogButton -> {
            if (dialogButton == connectButtonType) {
                // Verificar que los campos estén llenos
                if (databaseNameField.getText().isEmpty() || usernameField.getText().isEmpty() || passwordField.getText().isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error de conexión");
                    alert.setHeaderText("Faltan datos");
                    alert.setContentText("Por favor, llene todos los campos");
                    alert.showAndWait();
                    return null;
                }

                // Actualizar credenciales de conexión
                String url = "jdbc:mysql://127.0.0.1:3306/" + databaseNameField.getText();
                String user = usernameField.getText();
                String pass = passwordField.getText();
                Database.updateCredentials(url, user, pass);

                try {
                    // Obtener instancia de la base de datos
                    Database database = Database.getInstance();

                    // Si se seleccionó el checkbox de recordar información, guardar datos en un archivo o en la base de datos misma
                    // ...
                    if (rememberCheckbox.isSelected()) {
                        // ...
                    }
                    return database;
                } catch (SQLException e) {
                    // Mostrar mensaje de error en caso de que la conexión no se pueda establecer
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error de conexión");
                    alert.setHeaderText("No se pudo establecer la conexión con la base de datos");
                    alert.setContentText(e.getMessage());
                    alert.showAndWait();
                    return null;
                }
            }
            return null;
        });
    }
}
