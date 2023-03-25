package com.example.projectjavafx;

import com.example.projectjavafx.menu.MyMenuBar;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class HomePageController {

    @FXML
    private BorderPane borderPane;

    public void init(MyMenuBar menuBar, MyMenuBar menuBar2) {
        VBox vbox = new VBox();
        vbox.getChildren().addAll(menuBar.getMenuBar(), menuBar2.getMenuBar());
        borderPane.setTop(vbox);
    }
}