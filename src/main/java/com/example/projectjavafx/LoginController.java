package com.example.projectjavafx;

import com.example.projectjavafx.menu.MyMenuBar;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

import java.sql.SQLException;

public class LoginController {
    private UserServices userServices;
    @FXML
    private PasswordField passLogin;
    @FXML
    private TextField userLogin;
    @FXML
    private BorderPane borderPane;

    public LoginController() throws SQLException {
        userServices = new UserServices();
    }

    public void init(MyMenuBar menuBar) {
        borderPane.setTop(menuBar.getMenuBar());
    }

    @FXML
    protected void onLoginBttn() throws SQLException {
        if (userServices.validateUser(userLogin.getText(), passLogin.getText())) {
            System.out.println("Login OK");
            Session session = Session.getInstance();
            session.setUserActive(userLogin.getText(), true);
            session.setUsername(userLogin.getText());
            session.setIsAdmin(userServices.isAdmin(userLogin.getText()));
            Session.getInstance().getApp().showHomePage();
        } else {
            System.out.println("Login KO");
        }
    }
}