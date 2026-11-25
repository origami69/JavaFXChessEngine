package com.example.demo;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;


import java.util.ArrayDeque;
import java.util.Objects;
import java.util.Queue;

public class ChessBoard extends GridPane {

    private static final int SIZE = 8;
    private final Tile[][] tiles = new Tile[SIZE][SIZE];
    Queue<Piece> whitePieces = new ArrayDeque<Piece>(32);
    Queue<Piece> blackPieces = new ArrayDeque<Piece>(32);
    public ChessBoard() {
        for(int i = 0; i < 8; i++){
            whitePieces.add(new Piece(i, 6, "/com/example/demo/pieceAssets/wP.png"));
            blackPieces.add(new Piece(i, 1, "/com/example/demo/pieceAssets/bP.png"));
        }
        whitePieces.add(new Piece(0, 7, "/com/example/demo/pieceAssets/wR.png"));
        whitePieces.add(new Piece(1, 7, "/com/example/demo/pieceAssets/wN.png"));
        whitePieces.add(new Piece(2, 7, "/com/example/demo/pieceAssets/wB.png"));
        whitePieces.add(new Piece(3, 7, "/com/example/demo/pieceAssets/wQ.png"));
        whitePieces.add(new Piece(4, 7, "/com/example/demo/pieceAssets/wK.png"));
        whitePieces.add(new Piece(5, 7, "/com/example/demo/pieceAssets/wB.png"));
        whitePieces.add(new Piece(6, 7, "/com/example/demo/pieceAssets/wN.png"));
        whitePieces.add(new Piece(7, 7, "/com/example/demo/pieceAssets/wR.png"));
        blackPieces.add(new Piece(0, 0, "/com/example/demo/pieceAssets/bR.png"));
        blackPieces.add(new Piece(1, 0, "/com/example/demo/pieceAssets/bN.png"));
        blackPieces.add(new Piece(2, 0, "/com/example/demo/pieceAssets/bB.png"));
        blackPieces.add(new Piece(3, 0, "/com/example/demo/pieceAssets/bQ.png"));
        blackPieces.add(new Piece(4, 0, "/com/example/demo/pieceAssets/bK.png"));
        blackPieces.add(new Piece(5, 0, "/com/example/demo/pieceAssets/bB.png"));
        blackPieces.add(new Piece(6, 0, "/com/example/demo/pieceAssets/bN.png"));
        blackPieces.add(new Piece(7, 0, "/com/example/demo/pieceAssets/bR.png"));
        setAlignment(Pos.CENTER);
        getStyleClass().add("chess-board");
        getStylesheets().add(
                Objects.requireNonNull(getClass().getResource("/com/example/demo/css/boardBackground.css")).toExternalForm()
        );
        // Top letters
        for (int col = 0; col < SIZE; col++) {
            Label label = new Label(String.valueOf((char) ('a' + col)));
            label.getStyleClass().add("coordinate");
            label.setPrefSize(80, 25);
            label.setAlignment(Pos.CENTER);
            add(label, col + 1, 0);
        }

        // Left and right numbers
        for (int row = 0; row < SIZE; row++) {
            String number = String.valueOf(8 - row);

            Label left = new Label(number);
            left.getStyleClass().add("coordinate");
            left.setPrefSize(25, 80);
            left.setAlignment(Pos.CENTER);

            Label right = new Label(number);
            right.getStyleClass().add("coordinate");
            right.setPrefSize(25, 80);
            right.setAlignment(Pos.CENTER);

            add(left, 0, row + 1);
            add(right, 9, row + 1);
        }

        // Chess squares
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                Tile tile = new Tile(row, col);
                tiles[row][col] = tile;
                add(tile, col + 1, row + 1);
            }
        }

        // Bottom letters
        for (int col = 0; col < SIZE; col++) {
            Label label = new Label(String.valueOf((char) ('a' + col)));
            label.getStyleClass().add("coordinate");
            label.setPrefSize(80, 25);
            label.setAlignment(Pos.CENTER);
            add(label, col + 1, 9);
        }
        while(!whitePieces.isEmpty()){
            Piece getPiece = whitePieces.poll();
            tiles[getPiece.getY()][getPiece.getX()].setPiece(getPiece.getAsset());
        }
        while(!blackPieces.isEmpty()){
            Piece getPiece = blackPieces.poll();
            tiles[getPiece.getY()][getPiece.getX()].setPiece(getPiece.getAsset());
        }
    }

    public Tile getTile(int row, int col) {
        return tiles[row][col];
    }
}
