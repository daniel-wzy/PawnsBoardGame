package controller;

import model.PlayerColor;

/**
 * An object that handles actions performed within views. This handles mouse clicks, and
 * key presses.
 */
public interface PlayerActionListener {
  /**
   * Handles the user clicking on a specific cell in the board. Row and Col are model cords. Zero
   * index based.
   * @param row the row of the clicked cell.
   * @param col the column of the clicked cell.
   */
  void handleCellClick(int row, int col, PlayerColor color);

  /**
   * Handels a user clicking on a card in the players hand. Zero index based.
   * @param col to click on.
   * @param color of view that was clicked on.
   */
  void handleHandClick(int col, PlayerColor color);

  /**
   * Handles confirming a move from a player. Press "M" to confirm move.
   * @param color of player to confirm move for.
   */
  void confirmMove(PlayerColor color);

  /**
   * Handles passing a move from a player. Press "P" to pass.
   * @param color of player to confirm pass.
   */
  void confirmPass(PlayerColor color);

  /**
   * Posts a message to this listener.
   * @param color of player to associate message to.
   * @param message to display.
   */
  void displayMessage(PlayerColor color, String message);
}
