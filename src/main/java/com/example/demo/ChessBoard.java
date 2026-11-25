
package com.example.demo;

import java.awt.Graphics2D;

public class ChessBoard {

    private static final int SIZE = 8;

    private final Tile[][] tiles = new Tile[SIZE][SIZE];

    public ChessBoard() {
        // Create tiles
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                tiles[row][col] = new Tile(row, col);
            }
        }

        // Starting positions
        String backRank = "RNBQKBNR";

        for (int i = 0; i < SIZE; i++) {
            tiles[6][i].setPiece(new Piece(i, 6,
                    "/com/example/demo/pieceAssets/wP.png"));

            tiles[1][i].setPiece(new Piece(i, 1,
                    "/com/example/demo/pieceAssets/bP.png"));

            tiles[7][i].setPiece(new Piece(i, 7,
                    "/com/example/demo/pieceAssets/w" + backRank.charAt(i) + ".png"));

            tiles[0][i].setPiece(new Piece(i, 0,
                    "/com/example/demo/pieceAssets/b" + backRank.charAt(i) + ".png"));
        }
    }

    public void draw(Graphics2D g, int x, int y, int tileSize,
            int hiddenRow, int hiddenCol) {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                int tileX = x + col * tileSize;
                int tileY = y + row * tileSize;

                // Temporarily hide the piece being dragged
                if (row == hiddenRow && col == hiddenCol) {
                    Piece piece = tiles[row][col].getPiece();
                    tiles[row][col].removePiece();
                    tiles[row][col].draw(g, tileX, tileY, tileSize);
                    tiles[row][col].setPiece(piece);
                } else {
                    tiles[row][col].draw(g, tileX, tileY, tileSize);
                }
            }
        }
    }

    public Piece getPiece(int row, int col) {
        return tiles[row][col].getPiece();
    }

    public void movePiece(int fromRow, int fromCol,
            int toRow, int toCol) {
        Piece piece = tiles[fromRow][fromCol].getPiece();

        if (piece == null)
            return;

        tiles[fromRow][fromCol].removePiece();
        tiles[toRow][toCol].setPiece(piece);
    }
}
