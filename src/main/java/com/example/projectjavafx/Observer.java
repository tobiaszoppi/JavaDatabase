package com.example.projectjavafx;

import java.sql.SQLException;

public interface Observer {
    void update() throws SQLException;
}
