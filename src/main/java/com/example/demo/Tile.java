
package com.example.demo;

import java.awt.*;
import javax.swing.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Tile {

    private final int row;
    private final int col;
    private Piece piece;

    private static final Color LIGHT = new Color(0xF0D9B5);
    private static final Color DARK = new Color(0xB58863);

    private static final Map<String, Image> images = new HashMap<>();

    public Tile(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public void draw(Graphics2D g, int x, int y, int size) {
        // Draw background
        g.setColor((row + col) % 2 == 0 ? LIGHT : DARK);
        g.fillRect(x, y, size, size);

        // Draw piece
        if (piece != null) {
            Image image = getImage(piece);

            int imageSize = (int) (size * 0.85);
            int padding = (size - imageSize) / 2;

            g.drawImage(image,
                    x + padding, y + padding,
                    imageSize, imageSize, null);
        }
    }

    public static Image getImage(Piece piece) {
        return images.computeIfAbsent(
                piece.getAsset(),
                path -> new ImageIcon(Objects.requireNonNull(
                        Tile.class.getResource(path),
                        "Missing piece image: " + path)).getImage());
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public Piece getPiece() {
        return piece;
    }

    public void removePiece() {
        piece = null;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
