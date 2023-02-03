package com.example.projectjavafx;

import javafx.scene.control.Alert;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {
    //private static Connection connection;
    public static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://127.0.0.1:3306/kiosco";
        String user = "root";
        String pass = "643851";
        Connection connection = DriverManager.getConnection(url, user, pass);

        // Verificar si existe la base de datos
        DatabaseMetaData dbm = connection.getMetaData();
        ResultSet tables = dbm.getTables(null, null, "kiosco", null);
        if (tables.next()) {
            System.out.println("Creando base de datos...");
            try (Statement st = connection.createStatement()) {
                st.executeUpdate("CREATE DATABASE kiosco");
            }
        } else {
            System.out.println("Base de datos existente, ingresando...");
        }

        return connection;
    }

    // Accede a los nombres de los users y los devuelve en una lista
    protected List<String> getAllNames() throws SQLException {
        Connection connection = Database.getConnection();
        List<String> names = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("SELECT username FROM users")) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    names.add(resultSet.getString("username"));
                }
            }
        }
        return names;
    }
    // Verificar si existe el usuario
    protected boolean UserExist(String username) throws SQLException {
        Connection connection = Database.getConnection();
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE username = ?")) {
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        }
    }
    // Insertar usuario a la db
    protected boolean createUser(String username, String password) throws SQLException {
        Connection connection = Database.getConnection();
        PassEnc p = new PassEnc();

        List<String> passData = p.createPassword(password);
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO users (username, securePassword, saltValue) VALUES" +
                " (?, ?, ?)")) {
            statement.setString(1, username);
            statement.setString(2, passData.get(0));
            statement.setString(3, passData.get(1));
            return statement.executeUpdate() == 1;
        }
    }

    protected boolean deleteUser(String username) throws SQLException {
        Connection connection = Database.getConnection();
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM users WHERE username = ?")) {
            statement.setString(1, username);
            return statement.executeUpdate() == 1;
        }
    }
    // Comparar securePasswords.
    protected boolean checkPassword(String user, String password) {
        boolean result = false;
        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT securePassword, saltValue FROM users WHERE username = ?")) {
            statement.setString(1, user);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String storedSecurePassword = resultSet.getString("securePassword");
                    String storedSaltValue = resultSet.getString("saltValue");
                    result = PassBasedEnc.verifyUserPassword(password, storedSecurePassword, storedSaltValue);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al verificar sesión: " + e.getMessage());
        }
        return result;
    }


    // Alertas
    protected void showConfirmationAlert(String title, String header, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(title);
        alert.setTitle(header);
        alert.setContentText(message);
        alert.show();
    }

    protected void showErrorAlert(String title, String header, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(header);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }
}
