package viewOther.model;


import viewOther.controller.GameProgress;

/**
 * Represents the game logic for the Pawns Board game.
 */
public interface PawnsGame extends ReadonlyPawnsGame {

  /**
   * Starts the game by initializing players, board, and dealing cards.
   *
   * @param playerRed  The red player.
   * @param playerBlue The blue player.
   * @param handSize   The number of starting cards for each player.
   * @throws IllegalStateException    if the game has already started.
   * @throws IllegalArgumentException if handSize is invalid.
   */
  void startGame(IPlayer playerRed, IPlayer playerBlue, int handSize);

  /**
   * Places a card on the board at the given position, if valid.
   *
   * @param cardIdx index of card to place.
   * @param row  row index.
   * @param col  column index.
   * @throws IllegalArgumentException if the move is invalid.
   * @throws IllegalStateException    if there are not enough pawns.
   */
  void placeCardInPosition(int cardIdx, int row, int col);

  /**
   * Passes the current player's turn.
   */
  void passTurn();

  /**
   * Creates and returns a deep copy of the game state.
   * Used primarily for strategy simulation, such as in minimax-style strategies,
   * where we want to predict the result of a move without modifying the actual game.
   * Note: the copy must be independent and modifying the copy must not affect the original.
   *
   * @return new PawnsGame instance that is a deep copy of this game state.
   */
  PawnsGame deepCopy();

  /**
   * Adds a listner that will be notified when it is this player's turn.
   *
   * @param listener the listener to notify at start of the player's turn
   */
  void addGameStatusListener(GameProgress listener);
}
