package com.example.projectjavafx;

import java.sql.SQLException;
import java.util.List;

public class DatabaseService {

    private Database db;

    public DatabaseService() {
        try {
            db = Database.getInstance();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean createUser(String username, String password) throws SQLException {
        return db.createUser(username, password);
    }

    public boolean checkPassword(String username, String password) throws SQLException {
        return db.checkPassword(username, password);
    }

    public boolean userExist(String username) throws SQLException {
        return db.userExist(username);
    }

    public boolean isAdmin(String username) throws SQLException {
        return db.isAdmin(username);
    }

    public List<String> getAllNames() throws SQLException {
        return db.getAllNames();
    }

    public boolean deleteUser(String username) throws SQLException {
        return db.deleteUser(username);
    }

    public boolean setAdmin(String username) {
        return db.setAdmin(username);
    }
}
