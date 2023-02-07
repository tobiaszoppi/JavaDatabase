package com.example.projectjavafx;

import java.sql.SQLException;

public class UserServices {
    private DatabaseService db;

    public UserServices() throws SQLException {
       db = new DatabaseService();
    }

    public boolean handleRegistration(String username, String password) throws SQLException {
        if (username.isBlank() || password.isBlank()) {
            return false;
        }

        if (db.userExist(username)) {
            return false;
        }

        return db.createUser(username, password);
    }

    public boolean checkPassword(String username, String password) throws SQLException {
        return db.checkPassword(username, password);
    }

    public boolean validateUser(String username, String password) throws SQLException {
        if (!db.userExist(username)) {
            return false;
        }

        return checkPassword(username, password);
    }

    public boolean isAdmin(String username) throws SQLException {
        return db.isAdmin(username);
    }

}

