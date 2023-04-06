package com.example.projectjavafx.proyectos;

import com.example.projectjavafx.ventanas.VentanaBase;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class VentanaAlgoritmo extends VentanaBase {

    private TextArea inputTextArea;
    private GridPane gridPane;
    private int[][] grid;
    private int rowCount;
    private int colCount;

    public VentanaAlgoritmo(String title) {
        super(title, Modality.APPLICATION_MODAL);
        gridPane = new GridPane();
        inputTextArea = new TextArea();
        inputTextArea.setPrefHeight(100);
        inputTextArea.setWrapText(true);

        Label inputLabel = new Label("Input:");
        Button runButton = new Button("Run");
        runButton.setOnAction(event -> runAlgorithm());

        VBox inputBox = new VBox(10, inputLabel, inputTextArea, runButton);
        inputBox.setPadding(new Insets(10));

        BorderPane root = new BorderPane(gridPane, null, null, inputBox, null);

        Scene scene = new Scene(root, 600, 600);
        window.setScene(scene);
    }

    private void runAlgorithm() {
        String input = inputTextArea.getText();
        String[] inputRows = input.split("\\],\\[");
        rowCount = inputRows.length;
        colCount = inputRows[0].replaceAll("\\[|\\]", "").split(",").length;
        grid = new int[rowCount][colCount];

        for (int i = 0; i < rowCount; i++) {
            String[] rowValues = inputRows[i].replaceAll("\\[|\\]", "").split(",");
            for (int j = 0; j < colCount; j++) {
                grid[i][j] = Integer.parseInt(rowValues[j].trim());
            }
        }

        // Limpiar la grilla previa
        gridPane.getChildren().clear();

        // Configurar la grilla
        gridPane.setHgap(1);
        gridPane.setVgap(1);
        gridPane.setPadding(new Insets(10));
        gridPane.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, new BorderWidths(1))));

        // Agregar las celdas a la grilla
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < colCount; j++) {
                Rectangle rect = new Rectangle(40, 40);
                rect.setStroke(Color.BLACK);
                if (grid[i][j] == 1) {
                    rect.setFill(Color.DARKBLUE);
                } else {
                    rect.setFill(Color.LIGHTGRAY);
                }
                gridPane.add(rect, j, i);
            }
        }

        // Contar las islas cerradas y resaltarlas
        int count = 0;
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < colCount; j++) {
                if (grid[i][j] == 0) {
                    if (bfs(grid, i, j)) {
                        count++;
                        Rectangle rect = (Rectangle) gridPane.getChildren().get(i * colCount + j);
                        rect.setStroke(Color.RED);
                    }
                }
            }
        }
        System.out.println("Number of closed islands: " + count);
    }

    private boolean bfs(int[][] grid, int i, int j) {
        Queue<int[]> queue = new LinkedList<>();
        grid[i][j] = 1; // marca como vista
        queue.offer(new int[]{i, j});

        int[][] directions = {{1,0},{-1,0},{0,1},{0,-1}};
        boolean isClosed = true;
        while(queue.size() > 0) {
            int[] curr = queue.poll();
            int row = curr[0];
            int col = curr[1];

            if(row == 0 || row == grid.length-1 || col == 0 || col == grid[0].length-1){
                isClosed = false;
            }

            for (int[] dir : directions) {
                int newRow = row + dir[0];
                int newCol = col + dir[1];
                if (newRow >= 0 && newRow < grid.length && newCol >= 0 && newCol < grid[0].length && grid[newRow][newCol] == 0) {
                    queue.offer(new int[]{newRow, newCol});
                    grid[newRow][newCol] = 1;
                }
            }
        }

        return isClosed;
    }
}