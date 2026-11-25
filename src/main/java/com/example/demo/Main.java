
package com.example.demo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main extends JPanel implements Runnable {

  private static final int WIDTH = 800;
  private static final int HEIGHT = 600;
  private static final int FPS = 60;
  private static final int TILE_SIZE = 60;

  private Thread gameThread;
  private boolean running = false;

  private final ChessBoard board = new ChessBoard();

  // Drag and drop
  private Piece draggingPiece;
  private int dragRow, dragCol;
  private int mouseX, mouseY;

  public Main() {
    setPreferredSize(new Dimension(WIDTH, HEIGHT));
    setBackground(Color.BLACK);
    setDoubleBuffered(true);

    // Press Escape to close the game
    getInputMap(WHEN_IN_FOCUSED_WINDOW).put(
        KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
        "exit");

    getActionMap().put("exit", new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        System.exit(0);
      }
    });

    // Mouse input
    MouseAdapter mouse = new MouseAdapter() {

      @Override
      public void mousePressed(MouseEvent e) {
        int x = e.getX() - (WIDTH - TILE_SIZE * 8) / 2;
        int y = e.getY() - (HEIGHT - TILE_SIZE * 8) / 2;

        if (x < 0 || x >= TILE_SIZE * 8 ||
            y < 0 || y >= TILE_SIZE * 8)
          return;

        dragCol = x / TILE_SIZE;
        dragRow = y / TILE_SIZE;

        draggingPiece = board.getPiece(dragRow, dragCol);

        mouseX = e.getX();
        mouseY = e.getY();
        repaint();
      }

      @Override
      public void mouseDragged(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
        repaint();
      }

      @Override
      public void mouseReleased(MouseEvent e) {
        if (draggingPiece == null)
          return;

        int x = e.getX() - (WIDTH - TILE_SIZE * 8) / 2;
        int y = e.getY() - (HEIGHT - TILE_SIZE * 8) / 2;

        if (x >= 0 && x < TILE_SIZE * 8 &&
            y >= 0 && y < TILE_SIZE * 8) {
          board.movePiece(dragRow, dragCol,
              y / TILE_SIZE, x / TILE_SIZE);
        }

        draggingPiece = null;
        repaint();
      }
    };

    addMouseListener(mouse);
    addMouseMotionListener(mouse);
  }

  public void start() {
    running = true;
    gameThread = new Thread(this);
    gameThread.start();
  }

  @Override
  public void run() {
    double frameTime = 1_000_000_000.0 / FPS;
    long lastTime = System.nanoTime();
    double delta = 0;

    while (running) {
      long now = System.nanoTime();
      delta += (now - lastTime) / frameTime;
      lastTime = now;

      while (delta >= 1) {
        update();
        delta--;
      }

      repaint();

      try {
        Thread.sleep(1);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        running = false;
      }
    }
  }

  private void update() {
    // Game logic (60 updates per second)
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    Graphics2D g2d = (Graphics2D) g;

    int boardSize = TILE_SIZE * 8;
    int offsetX = (WIDTH - boardSize) / 2;
    int offsetY = (HEIGHT - boardSize) / 2;

    // Draw board
    board.draw(g2d, offsetX, offsetY, TILE_SIZE,
        draggingPiece != null ? dragRow : -1,
        draggingPiece != null ? dragCol : -1);

    // Draw coordinates
    g2d.setColor(Color.WHITE);
    g2d.setFont(new Font("Arial", Font.BOLD, 14));

    FontMetrics fm = g2d.getFontMetrics();

    for (int i = 0; i < 8; i++) {
      String letter = String.valueOf((char) ('a' + i));
      String number = String.valueOf(8 - i);

      int letterX = offsetX + i * TILE_SIZE
          + (TILE_SIZE - fm.stringWidth(letter)) / 2;

      g2d.drawString(letter, letterX, offsetY - 10);
      g2d.drawString(letter, letterX, offsetY + boardSize + 22);

      int numberY = offsetY + i * TILE_SIZE
          + (TILE_SIZE + fm.getAscent() - fm.getDescent()) / 2;

      g2d.drawString(number, offsetX - 20, numberY);
      g2d.drawString(number, offsetX + boardSize + 10, numberY);
    }

    // Draw dragged piece over everything else
    if (draggingPiece != null) {
      Image image = Tile.getImage(draggingPiece);

      int size = (int) (TILE_SIZE * 0.85);

      g2d.drawImage(image,
          mouseX - size / 2,
          mouseY - size / 2,
          size, size, null);
    }
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
      JFrame frame = new JFrame("Java 2D Game");
      Main game = new Main();

      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setResizable(false);
      frame.add(game);
      frame.pack();
      frame.setLocationRelativeTo(null);
      frame.setVisible(true);

      game.start();
    });
  }
}
