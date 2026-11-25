
package com.example.demo;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Launcher extends Application {

    @Override
    public void start(Stage stage) {

        ChessBoard board = new ChessBoard();

        StackPane root = new StackPane(board);

        Scene scene = new Scene(root, 800, 800);

        stage.setTitle("Chess Engine");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}