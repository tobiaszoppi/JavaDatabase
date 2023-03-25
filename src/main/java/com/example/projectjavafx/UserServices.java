package com.example.projectjavafx;

import com.example.projectjavafx.database.DatabaseService;

import java.sql.SQLException;
/*
La clase UserServices se encarga de realizar operaciones relacionadas con los usuarios,
como el registro y la validación de credenciales.
Contiene una instancia de la clase DatabaseService, que se utiliza para interactuar con la
base de datos y verificar información sobre los usuarios. La clase también contiene una
instancia de MessageService, que se utiliza para mostrar mensajes al usuario.
 */

public class UserServices {
    /*
    Encapsulamiento: La clase UserServices encapsula la lógica de negocio relacionada con el manejo de usuarios.
    Al mantener esta lógica en una sola clase, se reduce la complejidad del código y se mejora la mantenibilidad.

    Abstracción: La clase UserServices proporciona una interfaz pública que oculta los detalles internos de su
    implementación. Esto permite a los desarrolladores trabajar con la clase de manera eficiente sin tener
    que conocer los detalles de su funcionamiento.

    Polimorfismo: La clase UserServices puede ser sobrescrita por una clase hija para adaptar su comportamiento a
    las necesidades específicas de una aplicación. Esto permite reutilizar el código existente y mejorar la
    flexibilidad de la aplicación.

    Herencia: La clase UserServices puede ser utilizada como base para crear clases hijas que hereden sus atributos y métodos.
    Esto permite a los desarrolladores reutilizar código existente y mejorar la organización del código.

    Uso de estructuras de datos: La clase UserServices puede utilizar estructuras de datos como listas,
    diccionarios o sets para almacenar y manipular información sobre usuarios.
    Esto permite a la clase llevar a cabo operaciones eficientes sobre la información de usuario y
    mejorar la eficiencia de la aplicación.
*/
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

    public boolean handleRegistration(String username, String password, Boolean isAdmin) throws SQLException {
        if (username.isBlank() || password.isBlank()) {
            messageService.showErrorAlert("Error", "Error de registro", "Error de registro: No se ha introducido ningún usuario o contraseña");
            return false;
        }

        if (db.userExist(username)) {
            messageService.showErrorAlert("Registro", "Error en el registro", "Usuario " + username + " ya existe");
            return false;
        }
        messageService.showConfirmationAlert("Registro", "Registro exitoso", "Usuario " + username + " registrado correctamente, ya puede iniciar sesión!");
        return db.createUser(username, password, isAdmin);
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

