package com.example.projectjavafx.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/*
La clase "Database" es una clase que se encarga de realizar operaciones en una base de datos.
La base de datos en este caso es una base de datos MySQL y se conecta a través de JDBC.
La clase "Database" implementa el patrón de diseño Singleton, lo que significa que sólo
puede haber una instancia de esta clase a través de toda la aplicación.
En resumen, la clase "Database" es una clase importante que proporciona acceso a
la base de datos y realiza operaciones en la misma de manera segura y organizada.
 */

public class Database {
    /*
    Encapsulación: La clase Database encapsula los detalles de implementación de la conexión a la base de datos
    y proporciona una interfaz pública para interactuar con ella. Por ejemplo, los detalles de la conexión a
    la base de datos, como la dirección del servidor y las credenciales, están ocultos dentro de la
    clase y no están disponibles para otros componentes del sistema.
    Abstracción: La clase Database es una abstracción de la base de datos en sí misma.
    Proporciona una interfaz para realizar operaciones como ejecutar consultas y obtener resultados,
    sin tener que preocuparse por los detalles de cómo se realiza la conexión y se realizan las
    consultas en la base de datos.
    Polimorfismo: La clase Database puede ser utilizada con diferentes tipos de bases de datos,
    ya sea MySQL, PostgreSQL, etc. y puede ser extendida para proporcionar una implementación
    específica para cada una de ellas, sin afectar a otras partes del sistema.
     */
    private static Database instance;
    private final Connection connection;

    private static String url = "jdbc:mysql://127.0.0.1:3306/";
    private static String user = "";
    private static String pass = "";

    public static void updateCredentials(String newUrl, String newUser, String newPass) {
        url = newUrl;
        user = newUser;
        pass = newPass;
    }

    public Database() throws SQLException {
        connection = DriverManager.getConnection(url, user, pass);
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
    }

    public static Database getInstance() throws SQLException {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
//Este método verifica si la instancia existe, y si no es así, la crea. Si ya existe, simplemente la devuelve.
    }

    /*
    La clase "Database" también implementa métodos para acceder a los nombres de los usuarios en la base de datos,
    verificar si un usuario existe en la base de datos, crear usuarios en la base de datos,
    eliminar usuarios de la base de datos y comparar contraseñas para verificar si un usuario es válido.
    El código utiliza consultas preparadas (PreparedStatement) y consultas de selección (ResultSet)
    para realizar estas operaciones en la base de datos.
     */

    // Accede a los nombres de los users y los devuelve en una lista
    protected List<String> getAllNames() throws SQLException {
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
    protected boolean userExist(String username) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE username = ?")) {
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    // Insertar usuario a la db
    protected boolean createUser(String username, String password) throws SQLException {
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
    protected boolean createUser(String username, String password, boolean isAdmin) throws SQLException {
        PassEnc p = new PassEnc();
        List<String> passData = p.createPassword(password);
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO users (username, isAdmin, securePassword, saltValue) VALUES" +
                " (?, ?, ?, ?)")) {
            statement.setString(1, username);
            statement.setInt(2, isAdmin ? 1 : 0);
            statement.setString(3, passData.get(0));
            statement.setString(4, passData.get(1));
            return statement.executeUpdate() == 1;
        }
    }

    // Eliminar usuario
    protected boolean deleteUser(String username) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM users WHERE username = ?")) {
            statement.setString(1, username);
            return statement.executeUpdate() == 1;
        }
    }

    // Comparar securePasswords.
    protected boolean checkPassword(String username, String password) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("SELECT securePassword, saltValue FROM users WHERE username = ?")) {
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String storedSecurePassword = resultSet.getString("securePassword");
                    String storedSaltValue = resultSet.getString("saltValue");
                    return PassBasedEnc.verifyUserPassword(password, storedSecurePassword, storedSaltValue);
                }
            }
        }
        return false;
    }

    // Preguntar si el usuario está logeado:
    protected boolean isActive(String username) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("SELECT isActive FROM users WHERE username = ?")) {
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    if (resultSet.getInt("isActive") == 1) {
                        return true;
                    } else return false;
                }
            }
        }
        return false;
    }

    // Setteear si el usuario está logeado:
    protected boolean setIsActive(String username, boolean isActive) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("UPDATE users SET isActive = ? WHERE username = ?")) {
            statement.setInt(1, isActive ? 1 : 0);
            statement.setString(2, username);
            return statement.executeUpdate() == 1;
        }
    }

    // Preguntar si el usuario es admin.
    protected boolean isAdmin(String username) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("SELECT isAdmin FROM users WHERE username = ?")) {
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next() && resultSet.getBoolean("isAdmin")) {
                    return true;
                }
            }
        }
        return false;
    }

    // Set admin
    protected boolean setAdmin(String username) throws SQLException {
        if (isAdmin(username)) {
            try (PreparedStatement statement = connection.prepareStatement("UPDATE users SET isAdmin = 0 WHERE username = ?")) {
                statement.setString(1, username);
                return statement.executeUpdate() == 1;
            }
        } else {
            try (PreparedStatement statement = connection.prepareStatement("UPDATE users SET isAdmin = 1 WHERE username = ?")) {
                statement.setString(1, username);
                return statement.executeUpdate() == 1;
            }
        }
    }
}
