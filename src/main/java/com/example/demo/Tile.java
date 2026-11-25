package com.example.demo;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Objects;

public class Tile extends StackPane {

    private final int row;
    private final int col;

    private final ImageView pieceView = new ImageView();

    private static final Color LIGHT = Color.web("#F0D9B5");
    private static final Color DARK = Color.web("#B58863");

    public Tile(int row, int col) {
        this.row = row;
        this.col = col;

        Rectangle background = new Rectangle();
        background.setFill((row + col) % 2 == 0 ? LIGHT : DARK);

        background.widthProperty().bind(widthProperty());
        background.heightProperty().bind(heightProperty());

        setPrefSize(80, 80);
        setMinSize(0, 0);

        background.widthProperty().bind(widthProperty());
        background.heightProperty().bind(heightProperty());

        pieceView.setPreserveRatio(true);
        pieceView.fitWidthProperty().bind(widthProperty().multiply(0.85));
        pieceView.fitHeightProperty().bind(heightProperty().multiply(0.85));

        pieceView.getStyleClass().add("chess-piece");
        pieceView.setMouseTransparent(false);

        getChildren().addAll(background, pieceView);
    }

    public void setPiece(String imagePath) {
        Image image = new Image(
                Objects.requireNonNull(
                        getClass().getResourceAsStream(imagePath)
                )
        );
        pieceView.setImage(image);
    }

    public void removePiece() {
        pieceView.setImage(null);
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
