package com.example.projectjavafx.ventanas;

import javafx.stage.Modality;
import javafx.stage.Stage;

public class VentanaBase {
    protected Stage window;

    public VentanaBase(String title, Modality modality) {
        window = new Stage();
        window.initModality(modality);
        window.setTitle(title);
    }

    public void show() {
        window.show();
    }

    public void close() {
        window.close();
    }

    public void dispose() {
        window.close();
        window = null;
    }
}