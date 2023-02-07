package com.example.projectjavafx;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

import java.sql.SQLException;

public class LoginController {
    private static App app;
    private static boolean flag = false;
    private DatabaseService db;
    @FXML
    private PasswordField passLogin;
    @FXML
    private TextField userLogin;
    @FXML
    private BorderPane borderPane;

    public LoginController() {
        db = new DatabaseService();
    }

    public void init(MyMenuBar menuBar) {
        borderPane.setTop(menuBar.getMenuBar());
    }

    public void setApp(App app) {
        LoginController.app = app;
    }

    protected void showErrorAlert(String title, String headerText, String contentText) {
        db.showErrorAlert(title, headerText, contentText);
    }

    protected boolean isLoggedIn() {
        if (flag) {
            return true;
        } else {
            showErrorAlert("Error", "Error de sesión", "Error de sesión: no se encuentra dentro de una sesión");
            return false;
        }
    }

    protected void logout() {
        app.showLoginScene();
        flag = false;
    }

    protected boolean handleRegistration(String username, String password) throws SQLException {
        if (username.isBlank() || password.isBlank()) {
            showErrorAlert("Error", "Error de registro", "Error de registro: No se ha introducido ningún usuario o contraseña");
            return false;
        }

        if (db.userExist(username)) {
            showErrorAlert("Registro", "Error en el registro", "Usuario " + username + " ya existe");
            return false;
        }

        if (db.createUser(username, password)) {
            db.showConfirmationAlert("Registro", "Registro exitoso", "Usuario " + username + " registrado correctamente, ya puede iniciar sesión!");
            return true;
        } else {
            db.showErrorAlert("Registro", "Error en el registro", "Usuario " + username + " no registrado");
            return false;
        }
    }

    @FXML
    protected void onLoginBttn() throws SQLException {
        if (db.checkPassword(userLogin.getText(), passLogin.getText())) {
            System.out.println("Login OK");
            flag = true;
            Session session = Session.getInstance();
            session.setUsername(userLogin.getText());
            session.setIsAdmin(db.isAdmin(userLogin.getText()));
            app.showHomePage();
        } else {
            System.out.println("Login KO");
            db.showErrorAlert("Login", "Error en el login", "Usuario o contraseña incorrectos");
        }
    }
}