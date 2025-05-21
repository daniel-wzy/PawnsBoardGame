package model;

import controller.ModelActions;

/**
 * Represents a PawnsBoard game. A PAwnsBoard game is a model that can run pawnsboard
 * game based on the pawnsboard specifications.
 * Link: https://course.khoury.northeastern.edu/cs3500/hw_pawnsboard_01_assignment.html
 * All rows and moves are zero index based, and all grid coordinate type paramerters
 * (i.e. (x, y) OR (width, height)) refer to row, column on the game board.
 */
public interface IPawnsBoard extends ReadonlyPawnsBoard {

  /**
   * Makes a move on the board with the given player, using the card at a given hand indx
   * at the specified row and col.
   * @param color player to make move from.
   * @param handIndex handindex to get card from.
   * @param row row to place card.
   * @param col col to place card.
   * @throws IllegalArgumentException if hand index, row, or col are incorrect.
   * @throws IllegalStateException if card can not be placed.
   */
  void makeMove(PlayerColor color, int handIndex, int row, int col);

  /**
   * Passes a players turn.
   * @param color player to pass turn for.
   */
  void pass(PlayerColor color);

  /**
   * Returns an immutable copy of the board.
   *
   * @return a copy of the board
   */
  IPawnsBoard copy();

  /**
   * Adds a model action listener to the notification list to be notified when turns change.
   * @param listener to send notification to.
   */
  void addTurnListener(ModelActions listener);

  /**
   * Adds an action listener to the gnotification list to be notified when game is over.
   * @param listener to send notification to.
   */
  void addGameOverListener(ModelActions listener);

  /**
   * Sets the game to active, allowing players to make moves.
   */
  public void startGame();

  /**
   * Sets the deck contents for this game, associated with a player.
   * @param deckContents to associate with a player.
   * @param color color to associate contents with.
   */
  public void setDeckContents(String deckContents, PlayerColor color);

}
