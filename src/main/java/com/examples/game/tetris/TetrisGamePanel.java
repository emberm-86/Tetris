package com.examples.game.tetris;

import com.examples.game.tetris.service.ShapeFactory;
import com.examples.game.tetris.service.TetrisService;
import com.examples.game.tetris.common.GameState;
import com.examples.game.tetris.service.HighScoreService;
import com.examples.game.tetris.shape.AbstractShape;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.concurrent.CompletableFuture;

import static com.examples.game.tetris.common.Constants.COL_NUM;
import static com.examples.game.tetris.common.Constants.FASTER_DELAY;
import static com.examples.game.tetris.common.Constants.NORMAL_DELAY;
import static com.examples.game.tetris.common.Constants.ROW_NUM;
import static com.examples.game.tetris.shape.AbstractShape.SIZE;

/**
 * Game board.
 */
public class TetrisGamePanel extends JPanel {

    private static final long serialVersionUID = 44212121212L;

    private AbstractShape actShape;
    private final Color[][] fillingColors;
    private int score;
    private int delay;
    private boolean gameOver;
    private GameState gameState;

    TetrisGamePanel(JFrame mainFrame) {
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(COL_NUM * SIZE,
                ROW_NUM * SIZE));

        actShape = ShapeFactory.createShape(COL_NUM / 2 - 1, 0);

        delay = NORMAL_DELAY;

        fillingColors = new Color[COL_NUM][ROW_NUM];

        score = 0;

        gameState = GameState.MAIN_MENU;

        // The menu handling is defined here.
        mainFrame.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {

                if (gameState == GameState.STARTED) {
                    return;
                }

                if (gameState == GameState.FINISHED
                        && e.getKeyChar() != 'x') {
                    return;
                }

                switch (e.getKeyChar()) {

                    case '1': {
                        gameState = GameState.STARTED;

                        resetGame();

                        TetrisMove tetrisMove = new TetrisMove();

                        CompletableFuture.runAsync(tetrisMove)
                                .thenRun(() -> HighScoreService
                                        .showNewHighScoreEntryDialog(
                                                mainFrame.getRootPane(),
                                                score));
                        break;
                    }

                    case '2': {
                        HighScoreService
                                .showHighScores(mainFrame.getRootPane());
                        break;
                    }

                    case '3': {
                        System.exit(0);
                        break;
                    }

                    case 'x': {
                        gameState = GameState.MAIN_MENU;
                        repaint();
                        break;
                    }
                }
            }
        });
    }

    /**
     * Restore the initial state
     * before the new game session started.
     */
    private void resetGame() {
        for (int i = 0; i < COL_NUM; i++) {
            for (int j = 0; j < ROW_NUM; j++) {
                fillingColors[i][j] = Color.WHITE;
            }
        }

        gameOver = false;

        delay = NORMAL_DELAY;
    }

    /**
     * Showing the main menu in the repaint.
     */
    private void showMenu(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("default", Font.BOLD, 16));

        g.drawString("1. START GAME", COL_NUM * SIZE / 2 - 70, 150);
        g.drawString("2. HIGH SCORES", COL_NUM * SIZE / 2 - 70, 200);
        g.drawString("3. EXIT", COL_NUM * SIZE / 2 - 70, 250);
        g.drawString("x. MAIN MENU", COL_NUM * SIZE / 2 - 70, 300);
    }

    /**
     * If the user is in the main menu
     * then the options are shown,
     * otherwise the game session is active.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (gameState == GameState.MAIN_MENU) {
            showMenu(g);
        } else {
            playGame(g);
        }
    }

    /**
     * This method is called in the repaint
     * if the game session is active.
     */
    private void playGame(Graphics g) {
        for (int i = 0; i < COL_NUM; i++) {
            for (int j = 0; j < ROW_NUM; j++) {
                actShape.updateRotationState();

                if (TetrisService.isInShape(actShape.getPoints(), i, j)) {
                    g.setColor(actShape.getColor());
                    g.fillRect(i * SIZE, j * SIZE,
                            SIZE - 1, SIZE - 1);

                } else if (fillingColors[i][j] != Color.WHITE) {
                    g.setColor(fillingColors[i][j]);
                    g.fillRect(i * SIZE,
                            j * SIZE, SIZE - 1,
                            SIZE - 1);
                }
            }
        }

        g.setColor(Color.WHITE);
        g.setFont(new Font("default", Font.BOLD, 16));
        g.drawString("SCORE: " + score, 10, 20);

        if (gameOver) {
            g.setColor(Color.WHITE);
            g.drawString("GAME OVER!", COL_NUM * SIZE / 2 - 60,
                    ROW_NUM * SIZE / 2 + 15);

            g.setFont(new Font("default", Font.BOLD, 12));
            g.drawString("PRESS X TO RETURN TO THE MAIN MENU",
                    COL_NUM * SIZE / 2 - 120,
                    ROW_NUM * SIZE / 2 + 65);
        }
    }

    /**
     * This Runnable class represents the thread definition
     * of the movement of the actual tetromino.
     */
    class TetrisMove implements Runnable {

        @Override
        public void run() {

            while (!gameOver) {

                while (actShape != null &&
                        !TetrisService.isShapeLanded(actShape, fillingColors)) {

                    try {
                        int plusScore = TetrisService.clearRows(fillingColors);

                        score += plusScore;

                        repaint();

                        actShape.getRotationPoint().y += 1;

                        Thread.sleep(delay);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e.getLocalizedMessage(), e);
                    }
                }

                gameOver = actShape != null
                        && TetrisService.isShapeLanded(actShape, fillingColors)
                        && actShape.getPoints()[0].y <= 1;

                if (!gameOver) {
                    actShape = ShapeFactory.createShape(COL_NUM / 2 - 1, 0);
                }
            }

            repaint();

            gameState = GameState.FINISHED;
        }
    }

    /**
     * The user interactions are defined here.
     * User can move the tetromino with left, right cursors
     * increase the speed with the down,
     * change the state with the up key.
     */
    class KeyControl extends KeyAdapter {

        public void keyReleased(KeyEvent e) {
            if (gameState != GameState.STARTED) {
                return;
            }

            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                delay = NORMAL_DELAY;
            }
        }

        public void keyPressed(KeyEvent e) {
            if (gameState != GameState.STARTED) {
                return;
            }

            switch (e.getKeyCode()) {

                case KeyEvent.VK_UP:
                    if (actShape.isRotatable(fillingColors) && !TetrisService
                            .isShapeLanded(actShape, fillingColors)) {
                        actShape.setState(actShape.getState().next());
                        actShape.updateRotationState();
                    }
                    break;

                case KeyEvent.VK_DOWN:
                    delay = FASTER_DELAY;
                    break;

                case KeyEvent.VK_LEFT:
                    TetrisService.moveLeft(actShape, fillingColors);
                    break;

                case KeyEvent.VK_RIGHT:
                    TetrisService.moveRight(actShape, fillingColors);
                    break;
            }
        }
    }
}