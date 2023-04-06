package com.example.projectjavafx.database;

import com.example.projectjavafx.Session;
import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Optional;

public class DatabaseDialog extends Dialog<Database> {
    private final TextField databaseNameField = new TextField();
    private final TextField usernameField = new TextField();
    private final PasswordField passwordField = new PasswordField();
    private final CheckBox rememberCheckbox = new CheckBox("Recordar esta información");

    public DatabaseDialog() {
        setTitle("Conexión a la base de datos");
        setHeaderText("Ingrese los datos para conectarse a la base de datos");
        setContentText("Si quiere crear o ingresar debe completar los siguientes datos:");

        // Crear layout para la ventana y agregar campos
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.addRow(0, new Label("Nombre de la base de datos:"), databaseNameField);
        gridPane.addRow(1, new Label("Usuario:"), usernameField);
        gridPane.addRow(2, new Label("Contraseña:"), passwordField);
        gridPane.add(rememberCheckbox, 0, 3, 2, 1);

        getDialogPane().setContent(gridPane);

        // Agregar botones de conectar, crear
        ButtonType connectButtonType = new ButtonType("Conectar", ButtonBar.ButtonData.OK_DONE);
        ButtonType createButtonType = new ButtonType("Create DB", ButtonBar.ButtonData.OK_DONE);
        getDialogPane().getButtonTypes().addAll(connectButtonType, createButtonType);

        // Establecer comportamiento al cerrar la ventana
        Platform.runLater(() -> {
            Button closeButton = (Button) getDialogPane().lookupButton(ButtonType.CLOSE);
            closeButton.setOnAction(event -> Platform.exit());
        });

        // Cargar credenciales de conexión
        if (Files.exists(Paths.get("credentials.txt"))) {
            loadCredentials();
        }

        Callback<ButtonType, Database> resultConverter = buttonType -> {
            if (buttonType == connectButtonType && verifyCredentials()) {

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
                        saveCredentials();
                    }
                    return database;
                } catch (SQLException e) {
                    // Mostrar mensaje de error en caso de que la conexión no se pueda establecer
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error de conexión");
                    alert.setHeaderText("No se pudo establecer la conexión con la base de datos");
                    alert.setContentText(e.getMessage());
                    alert.showAndWait();
                }
            } else if (buttonType == createButtonType && verifyCredentials()) {
                // Crear base de datos
                createDatabase();
            }
            return null;
        };
        // Establecer el objeto Callback como el setResultConverter()
        setResultConverter(resultConverter);
    }

    private boolean verifyCredentials() {
        // Verificar que los campos estén llenos
        if (databaseNameField.getText().isEmpty() || usernameField.getText().isEmpty() || passwordField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de conexión");
            alert.setHeaderText("Faltan datos");
            alert.setContentText("Por favor, llene todos los campos");
            alert.showAndWait();
            return false;
        } else return true;
    }

    private void saveCredentials() {
        String databaseName = databaseNameField.getText();
        String username = usernameField.getText();

        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get("credentials.txt"))) {
            writer.write(databaseName + "," + username);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadCredentials() {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("credentials.txt"))) {
            String[] credentials = reader.readLine().split(",");
            databaseNameField.setText(credentials[0]);
            usernameField.setText(credentials[1]);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createDatabase() {
        String databaseName = databaseNameField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();

        try {
            if (databaseExists(databaseName, username, password)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error al crear la base de datos");
                alert.setHeaderText("La base de datos ya existe.");
                alert.showAndWait();
            } else {
                try (Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/", username, password)) {
                    Statement statement = connection.createStatement();
                    statement.executeUpdate("CREATE DATABASE " + databaseName);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Base de datos creada");
                    alert.setHeaderText("La base de datos se ha creado correctamente.");
                    alert.showAndWait();
                }
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error al crear la base de datos");
            alert.setHeaderText("No se pudo crear la base de datos");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    private boolean databaseExists(String databaseName, String username, String password) throws SQLException {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/", username, password)) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SHOW DATABASES");
            while (resultSet.next()) {
                if (resultSet.getString(1).equalsIgnoreCase(databaseName)) {
                    return true;
                }
            }
            return false;
        }
    }
}
