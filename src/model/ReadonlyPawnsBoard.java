package model;

import java.util.List;
import java.awt.Point;
import java.util.Map;

/**
 * Represents a read only PawnsBoard game. A PAwnsBoard game is a model that can run pawnsboard
 * game based on the pawnsboard specifications.
 * Link: https://course.khoury.northeastern.edu/cs3500/hw_pawnsboard_01_assignment.html
 * All rows and moves are zero index based, and all grid coordinate type paramerters
 * (i.e. (x, y) OR (width, height)) refer to row, column on the game board.
 */
public interface ReadonlyPawnsBoard {
  /**
   * Gets the current turn of the game. Returns the last player turn if game is over.
   * @return
   */
  PlayerColor getTurn();

  /**
   * Returns the red players current hand as an array list.
   * @return arraylist of cards of players current hand.
   */
  List<Card> getRedHand();

  /**
   * Returns the blue players current hand as an array list.
   * @return arraylist of cards of players current hand.
   */
  List<Card> getBlueHand();

  /**
   * Returns true if the game is over, false if the game is still in progress.
   * @return true or false representing game state.
   */
  boolean isGameOver();

  /**
   * Checks if a move on the board with the given player, using the card at a given hand indx
   * at the specified row and col is valid.
   * @param color player to make move from.
   * @param handIndex handindex to get card from.
   * @param row row to place card.
   * @param col col to place card.
   */
  boolean isValid(PlayerColor color, int handIndex, int row, int col);

  /**
   * Returns a copy of the board for this model.
   * @return an 2D arraylist representing this board. (0 index based).
   */
  List<List<Cell>> getBoard();

  /**
   * Gets the scores for each row as a list of Map entries. The key represents reds score for the
   * associated row, and the value represents the blue score.
   * @return an array list of map entries represent row scores.
   */
  List<Map.Entry<Integer, Integer>> getScores();

  /**
   * Returns the players current scores. Key represents Red player, value represents blue player.
   * @return Key value pair representing score.
   */
  // TODO: Switch to map with playerenuym as key and score as value.
  Map.Entry<Integer, Integer> getScore();

  /**
   * Gets the width of this model's board.
   * @return an int representing the width of this board.
   */
  int getWidth();

  /**
   * Returns the height of this model's board.
   * @return an int representing the height of this models board.
   */
  int getHeight();

  /**
   * Returns the copy of the cell at the given coordinate.
   * @return a copy of the cell.
   */
  Cell getCell(int r, int c);

  /**
   * Geta the hand that is currently highlighted given a specific player.
   * @param color of player to assoicate with highlight.
   * @return an int representing the card index, -1 if no card is highlighted.
   */
  int getHandHighlighted(PlayerColor color);

  /**
   * Gets a copy of the desired player color.
   * @param color to select from.
   * @return a copy of the player.
   */
  Player getPlayer(PlayerColor color);

  /**
   * Sets the current hand to be highlighted. Only effects view and control
   * of model.
   * @param handHighlighted index to highlight.
   * @param color to highlight for.
   * @return an int representing the new index.
   */
  int setHandHighlighted(int handHighlighted, PlayerColor color);

  /**
   * Gets the current cell that is highlighted.
   * @param color of player to get the highlighted cell for.
   * @return a point representing the row, col of the cell that has been highlighted.
   */
  Point getCellHighlighted(PlayerColor color);

  /**
   * Sets a certain cell as highlighted.
   * @param point to highlight based on row, and col.
   * @param color to highlight cell for.
   * @return a point representing the new cell that is highlighted, with row, col.
   */
  Point setCellHighlighted(Point point, PlayerColor color);

  /**
   * Copies the model, returning an immutable copy.
   * @return a copy of the IPawnsBoard.
   */
  IPawnsBoard copy();
}
