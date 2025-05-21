package viewOther.model;

import java.util.List;

/**
 * A read-only pawns game interface with not mutatble mehtods.
 */
public interface ReadonlyPawnsGame {

  /**
   * Returns the board state as a copy.
   *
   * @return copu of the board state.
   */
  ICell[][] getBoardState();

  /**
   * Returns the score for a given row.
   *
   * @param row The row index.
   * @return scores for the red and blue players.
   * @throws IllegalArgumentException if the row is invalid.
   */
  int[] getRowScore(int row);

  /**
   * Returns the winner of the game, or null if there is a tie.
   *
   * @return the winning player or null if it's a tie.
   */
  IPlayer getWinner();

  /**
   * Returns whether the game is over.
   *
   * @return true if both players have passed their turns, otherwise false.
   */
  boolean isGameOver();

  /**
   * Returns the current player whose turn it is.
   *
   * @return the current player.
   */
  IPlayer getCurrentPlayer();

  /**
   * Checks if placing a given card at the given position is a valid move.
   *
   * @param cardIdx index of card to be placed.
   * @param row  row index
   * @param col  column index
   * @return true if the move is valid, false otherwise.
   */
  boolean isValidMove(int cardIdx, int row, int col);

  /**
   * Returns the number of rows in the board.
   *
   * @return number of rows.
   */
  int getNumRows();

  /**
   * Returns the number of columns in the board.
   *
   * @return number of columns.
   */
  int getNumCols();

  /**
   * Returns the cell at the specified coordinates.
   *
   * @param row the row index.
   * @param col the column index.
   * @return the cell at the given coordinates.
   */
  ICell getCellAt(int row, int col);

  /**
   * Returns a copy of the player's hand.
   *
   * @param color the color of the player.
   * @return a list of cards in the player's hand.
   * @throws IllegalArgumentException if the game has not started or the color is invalid.
   */
  List<ICard> getPlayerHand(PlayerColor color);

  /**
   * Returns the owner of the cell at the given coordinates, or null if unowned.
   *
   * @param row the row index of the cell
   * @param col the column index of the cell
   * @return the player who owns the card or pawns in the cell, or null if unowned
   * @throws IllegalArgumentException if the cell position is invalid
   */
  IPlayer getCellOwner(int row, int col);

  /**
   * Gets the total score for a given player.
   *
   * @param color the player's color.
   * @return that player’s score.
   */
  int getPlayerScore(PlayerColor color);

  /**
   * Gets the influence grid for a card, mirrored for blue player.
   */
  int[][] getCardInfluenceGrid(PlayerColor color, ICard card);

}