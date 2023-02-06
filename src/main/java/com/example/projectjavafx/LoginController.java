package com.example.projectjavafx;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

import java.sql.SQLException;

/*
terminar de pasar los metodos a database y hacer el algoritmo,
testear el create user.
 */

public class LoginController {
    //Atributos de clase
    private static App app;
    static boolean flag = false;
    Database db;
    {
        try {
            db = Database.getInstance();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //Atributos de la interfaz
    @FXML
    private PasswordField passLogin;
    @FXML
    private TextField userLogin;
    @FXML
    private BorderPane borderPane;

    // Metodos de la interfaz
    public void init(MyMenuBar menuBar) {
        borderPane.setTop(menuBar.getMenuBar());
    }

    // Metodos generales
    public void setApp(App app) {
        LoginController.app = app;
    }
    protected boolean isloggedIn() {
        if (flag) {
            return true;
        } else {
            db.showErrorAlert("Error", "Error de sesion", "Error de sesion: no se encuentra dentro de una sesion");
            return false;
        }
    }
    protected void logout() {
        app.showLoginScene();
        flag = false;
    }
    protected boolean handleRegistration(String username, String password) throws SQLException {
        try {
            if (username.isBlank() || password.isBlank()) {
                db.showErrorAlert("Error", "Error de registro", "Error de registro: No se ha introducido ningun usuario o contraseña");
                return false;
            }
            if (db.UserExist(username)) {
                db.showErrorAlert("Registro", "Error en el registro","Usuario " + username +" ya existe");
            } else if (db.createUser(username, password)) {
                db.showConfirmationAlert("Registro","Registro exitoso", "Usuario " + username + " registrado correctamente, ya puede iniciar sesión!");
                return true;
            } else {
                db.showErrorAlert("Registro","Error en el registro", "Usuario " + username + " no registrado");
            }
        } catch (SQLException ex) {
            db.showErrorAlert("Registro", "Error en el registro", "Error al conectarse a la base de datos: " + ex.getMessage());
        }
        return false;
    }

    @FXML
    protected void onLoginBttn() {
        if (db.checkPassword(userLogin.getText(), passLogin.getText())) {
            System.out.println("Login OK");
            flag = true; // flag
            app.showHomePage();
        } else {
            System.out.println("Login KO");
            db.showErrorAlert("Login", "Error en el login", "Usuario o contraseña incorrectos");
        }
    }
}