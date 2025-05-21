package model;

import java.util.Map;
import java.util.List;

/**
 * Represents the game board in the PawnsBoard game. It is responsible for managing the grid
 * and game state.
 */
public interface Board {
  /**
   * Retrieves the value of a specific cell on the board.
   *
   * @param row The row index of the cell.
   * @param col The column index of the cell.
   * @return The value of the specified cell.
   * @throws IllegalArgumentException if the cell position is invalid.
   */
  Cell getCell(int row, int col);

  /**
   * Returns the current state of the board.
   *
   * @return The current board state as an array list of strings.
   */
  List<List<Cell>> getBoard();

  /**
   * Get the current width of this board.
   *
   * @return an int representing the width of this board.
   */
  int getWidth();

  /**
   * Get the current height of this board.
   *
   * @return an int reprsenting the height of this board.
   */
  int getHeight();

  /**
   * Gets the current scores of each row for the entire board.
   * The key represents Red total score for any given row.
   * The value represents the Blue total score for any given row.
   *
   * @return a map of row by row scores.
   */
  List<Map.Entry<Integer, Integer>> getScores();

  /**
   * Places a card on the board and applies its influence.
   *
   * @param row   row to place.
   * @param col   column to place at.
   * @param card  card to place.
   * @param color to place for.
   */
  void placeCard(int row, int col, Card card, PlayerColor color);

  /**
   * Checks if a given position fits the given bounds of the board and valid.
   *
   * @param row The row index.
   * @param col The column index.
   * @return true if the position is within the bounds and false if not.
   */
  boolean isValidPosition(int row, int col, PlayerColor color, Cell target);

  /**
   * Returns an immutable copy of the game board.
   *
   * @return the model's board
   */
  Board copy();


}
