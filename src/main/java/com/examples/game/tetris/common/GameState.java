package com.examples.game.tetris.common;

/**
 * Enum for checking in user navigation. If a game session is finished then the user can return by
 * '3' to the main menu.
 */
public enum GameState {
  MAIN_MENU,
  STARTED,
  FINISHED
}