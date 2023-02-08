package com.example.projectjavafx;

import java.sql.SQLException;

public class UserServices {
    private final DatabaseService db;

    private final MessageService messageService = new MessageService();

    public UserServices() {
        db = new DatabaseService();
    }

    public boolean handleRegistration(String username, String password) throws SQLException {
        if (username.isBlank() || password.isBlank()) {
            messageService.showErrorAlert("Error", "Error de registro", "Error de registro: No se ha introducido ningún usuario o contraseña");
            return false;
        }

        if (db.userExist(username)) {
            messageService.showErrorAlert("Registro", "Error en el registro", "Usuario " + username + " ya existe");
            return false;
        }
        messageService.showConfirmationAlert("Registro", "Registro exitoso", "Usuario " + username + " registrado correctamente, ya puede iniciar sesión!");
        return db.createUser(username, password);
    }

    public boolean checkPassword(String username, String password) throws SQLException {
        return db.checkPassword(username, password);
    }

    public boolean validateUser(String username, String password) throws SQLException {
        if (!db.userExist(username)) {
            messageService.showErrorAlert("Error", "Error de sesión", "Error de sesión: No se ha encontrado el usuario " + username);
            return false;
        }
        if (checkPassword(username, password)) {
            return true;
        } else {
            messageService.showErrorAlert("Sesión", "Error de sesión", "Error de sesión: Contraseña incorrecta");
            return false;
        }
    }

    public boolean isAdmin(String username) throws SQLException {
        return db.isAdmin(username);
    }

}

