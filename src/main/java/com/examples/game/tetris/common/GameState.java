package com.examples.game.tetris.common;

/**
 * Enum for checking in user navigation.
 * If a game session is finished then
 * the user can return by 'x' to the main menu.
 */
public enum GameState {

    MAIN_MENU('0'),
    STARTED('1'),
    FINISHED('2');

    private char key;

    GameState(char key) {
        this.key = key;
    }
}
