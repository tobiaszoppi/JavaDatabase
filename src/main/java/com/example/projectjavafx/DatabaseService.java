package com.example.projectjavafx;

import java.sql.SQLException;
import java.util.List;
/*
La clase DatabaseService es una clase que actúa como un intermediario entre su aplicación y la
clase Database. Es decir, proporciona una capa de abstracción que oculta los detalles de implementación
de la clase Database y proporciona una interfaz más simple y fácil de usar para las aplicaciones
que necesitan acceder a los datos en la base de datos.
En general, esta clase se utiliza para mejorar la modularidad y la mantenibilidad de la aplicación,
ya que proporciona una interfaz más fácil de usar para las aplicaciones que acceden a la base de datos,
y permite que la lógica de acceso a datos se centralice en un solo lugar. También puede ser útil para
proporcionar un nivel adicional de seguridad al ocultar los detalles de implementación de la
clase Database y proporcionar una interfaz más segura para acceder a los datos.
 */

public class DatabaseService {
    /*
    Encapsulación: La clase DatabaseService encapsula la lógica de negocio relacionada con la base de datos,
    por ejemplo, la creación de usuarios, la autenticación y la gestión de sesiones.
    Herencia: La clase DatabaseService puede heredar métodos y atributos de la clase Database,
    lo que le permite utilizar la funcionalidad de conexión a la base de datos sin tener que reescribirla.
     */

    private final Database db;

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

    public boolean createUser(String username, String password, boolean isAdmin) throws SQLException {
        return db.createUser(username, password, isAdmin);
    }

    public boolean checkPassword(String username, String password) throws SQLException {
        return db.checkPassword(username, password);
    }

    public boolean userExist(String username) throws SQLException {
        return db.userExist(username);
    }

    public List<String> getAllNames() throws SQLException {
        return db.getAllNames();
    }

    public boolean deleteUser(String username) throws SQLException {
        return db.deleteUser(username);
    }

    public boolean isAdmin(String username) throws SQLException {
        return db.isAdmin(username);
    }

    public boolean setAdmin(String username) throws SQLException {
        return db.setAdmin(username);
    }

    public boolean isActive(String username) throws SQLException {
        return db.isActive(username);
    }

    public boolean setIsActive(String username, boolean isActive) throws SQLException {
        return db.setIsActive(username,isActive);
    }
}
