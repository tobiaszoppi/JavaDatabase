package com.example.projectjavafx;

import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class HomePageController {

    @FXML
    private BorderPane borderPane;
    private VBox vbox;

    public void init(MyMenuBar menuBar, MyMenuBar menuBar2) {
        vbox = new VBox();
        vbox.getChildren().addAll(menuBar.getMenuBar(), menuBar2.getMenuBar());
        borderPane.setTop(vbox);
    }
}