package com.games.tetris;

import javax.swing.JFrame;
import java.awt.BorderLayout;

/**
 * Tetris game with SRS rotation system.
 *
 * @author Matyas Ember
 */
public class TetrisGame extends JFrame {

    private static final long serialVersionUID = 1L;

    private static final String GAME_TITLE = "Tetris v1.0";

    public TetrisGame(int x, int y) {
        setLayout(new BorderLayout());
        setLocation(x, y);
        setTitle(GAME_TITLE);

        TetrisGamePanel panel = new TetrisGamePanel(this);
        add(panel, BorderLayout.CENTER);
        addKeyListener(panel.new KeyControl());
        pack();

        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new TetrisGame(100, 200);
    }
}