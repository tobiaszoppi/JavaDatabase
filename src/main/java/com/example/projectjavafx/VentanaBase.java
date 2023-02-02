package com.example.projectjavafx;

import javafx.stage.Modality;
import javafx.stage.Stage;

public class VentanaBase {
    Stage window;

    public VentanaBase(String title) {
        window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
    }

    public void show() {
        window.show();
    }

    public void close() {
        window.close();
    }
}