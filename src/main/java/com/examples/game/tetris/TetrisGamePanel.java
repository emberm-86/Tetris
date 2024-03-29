package com.examples.game.tetris;

import static com.examples.game.tetris.common.Constants.BG_COLOR;
import static com.examples.game.tetris.common.Constants.COL_NUM;
import static com.examples.game.tetris.common.Constants.FASTER_DELAY;
import static com.examples.game.tetris.common.Constants.MENU_COLOR;
import static com.examples.game.tetris.common.Constants.NORMAL_DELAY;
import static com.examples.game.tetris.common.Constants.ROW_NUM;
import static com.examples.game.tetris.shape.AbstractShape.RECT_SIZE;

import com.examples.game.tetris.common.GameState;
import com.examples.game.tetris.service.ShapeFactory;
import com.examples.game.tetris.service.TetrisService;
import com.examples.game.tetris.shape.AbstractShape;
import com.examples.game.tetris.util.HighScoreUtil;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Game board.
 */
public class TetrisGamePanel extends JPanel {

  private static final long serialVersionUID = 44212121212L;

  private static final Font SMALLER_FONT = new Font("default", Font.BOLD, 12);
  private static final Font BIGGER_FONT = new Font("default", Font.BOLD, 16);

  private AbstractShape actShape;
  private final Color[][] fillingColors;
  private int score;
  private int delay;
  private boolean gameOver;
  private GameState gameState;
  private final TetrisService tetrisService;

  public TetrisGamePanel(JFrame mainFrame, TetrisService tetrisService) {
    setBackground(BG_COLOR);
    setPreferredSize(new Dimension(COL_NUM * RECT_SIZE, ROW_NUM * RECT_SIZE));

    this.tetrisService = tetrisService;
    this.fillingColors = new Color[COL_NUM][ROW_NUM];
    this.gameState = GameState.MAIN_MENU;

    mainFrame.addKeyListener(new KeyAdapter() {

      @Override
      public void keyPressed(KeyEvent e) {
        if (gameState == GameState.STARTED) {
          return;
        }

        if (gameState == GameState.FINISHED && e.getKeyChar() != '3') {
          return;
        }

        switch (e.getKeyChar()) {
          case '1': {
            gameState = GameState.STARTED;
            resetGame();
            TetrisMove tetrisMove = new TetrisMove();
            CompletableFuture.runAsync(tetrisMove).thenRun(
                () -> HighScoreUtil.showNewHighScoreEntryDialog(mainFrame.getRootPane(), score));
            break;
          }

          case '2': {
            HighScoreUtil.showHighScores(mainFrame.getRootPane());
            break;
          }

          case '3': {
            gameState = GameState.MAIN_MENU;
            repaint();
            break;
          }

          case 'x': {
            System.exit(0);
            break;
          }
        }
      }
    });
  }

  /**
   * Drawing background to check if tetromino fits correctly.
   */
  public void drawBackGround(Graphics g) {
    g.setColor(Color.WHITE);

    for (int i = 1; i <= COL_NUM; i++) {
      for (int j = 1; j <= ROW_NUM; j++) {
        g.drawLine(i * RECT_SIZE - 1, j * RECT_SIZE - 1,
            i * RECT_SIZE - 1, j * RECT_SIZE - 1);
      }
    }
  }

  /**
   * Restore the initial state before the new game session started.
   */
  private void resetGame() {
    for (int i = 0; i < COL_NUM; i++) {
      for (int j = 0; j < ROW_NUM; j++) {
        fillingColors[i][j] = BG_COLOR;
      }
    }
    gameOver = false;
    score = 0;
    delay = NORMAL_DELAY;
  }

  /**
   * Showing the main menu in the repaint.
   */
  private void showMenu(Graphics g) {
    g.setColor(MENU_COLOR);
    g.setFont(BIGGER_FONT);

    List<String> menuOpts = List.of("1. START GAME", "2. HIGH SCORES", "3. MAIN MENU", "x. EXIT");
    IntStream.range(0, menuOpts.size())
        .forEach(i -> g.drawString(menuOpts.get(i), COL_NUM * RECT_SIZE / 2 - 70, 150 + i * 50));
  }

  /**
   * If the user is in the main menu then the options are shown, otherwise the game session is
   * active.
   */
  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    if (gameState == GameState.MAIN_MENU) {
      showMenu(g);
    } else {
      playGame(g);
      drawBackGround(g);
    }
  }

  /**
   * This method is called in the repaint if the game session is active.
   */
  private void playGame(Graphics g) {
    if (actShape != null) {
      actShape.updateRotationState();
    }

    for (int i = 0; i < COL_NUM; i++) {
      for (int j = 0; j < ROW_NUM; j++) {
        Color color = actShape != null && tetrisService.isInActShape(actShape.getPoints(), i, j)
            ? actShape.getColor() : fillingColors[i][j];
        g.setColor(color);
        g.fillRect(i * RECT_SIZE, j * RECT_SIZE, RECT_SIZE - 1, RECT_SIZE - 1);
      }
    }

    g.setColor(MENU_COLOR);
    g.setFont(BIGGER_FONT);
    g.drawString("SCORE: " + score, 10, 20);

    if (gameOver) {
      g.setColor(MENU_COLOR);
      g.drawString("GAME OVER!", COL_NUM * RECT_SIZE / 2 - 60, ROW_NUM * RECT_SIZE / 2 + 15);
      g.setFont(SMALLER_FONT);
      g.drawString("PRESS '3' TO RETURN TO THE MAIN MENU.",
          COL_NUM * RECT_SIZE / 2 - 120, ROW_NUM * RECT_SIZE / 2 + 65);
    }
  }

  /**
   * This Runnable class represents the task definition of the actual tetromino's movement.
   */
  class TetrisMove implements Runnable {

    @Override
    public void run() {
      actShape = ShapeFactory.createShape(COL_NUM / 2 - 1, 0);

      while (!gameOver) {
        if (actShape == null) {
          gameOver = true;
          break;
        }

        while (!tetrisService.isActShapeCannotBeLanded(actShape, fillingColors)
            && tetrisService.isActShapeNotLanded(actShape, fillingColors)) {
          try {
            score += tetrisService.clearRows(fillingColors);
            repaint();
            actShape.getRotationPoint().y += 1;
            Thread.sleep(delay);
          } catch (InterruptedException e) {
            throw new RuntimeException(e.getLocalizedMessage(), e);
          }
        }

        gameOver = actShape.getRotationPoint().y == 0;
        actShape = !gameOver ? ShapeFactory.createShape(COL_NUM / 2 - 1, 0) : null;
      }

      repaint();
      gameState = GameState.FINISHED;
    }
  }

  /**
   * The user interactions are defined here. User can move the tetromino with left, right cursors
   * increase the speed with the down, change the state with the up key.
   */
  class KeyControl extends KeyAdapter {

    @Override
    public void keyReleased(KeyEvent e) {
      if (gameState != GameState.STARTED) {
        return;
      }

      if (e.getKeyCode() == KeyEvent.VK_DOWN) {
        delay = NORMAL_DELAY;
      }
    }

    @Override
    public void keyPressed(KeyEvent e) {
      if (gameState != GameState.STARTED) {
        return;
      }

      switch (e.getKeyCode()) {
        case KeyEvent.VK_UP:
          if (actShape.isRotatable(fillingColors) && tetrisService
              .isActShapeNotLanded(actShape, fillingColors)) {
            actShape.setState(actShape.getState().next());
            actShape.updateRotationState();
          }
          break;

        case KeyEvent.VK_DOWN:
          delay = FASTER_DELAY;
          break;

        case KeyEvent.VK_LEFT:
          tetrisService.moveLeft(actShape, fillingColors);
          break;

        case KeyEvent.VK_RIGHT:
          tetrisService.moveRight(actShape, fillingColors);
          break;
      }
    }
  }
}