package model;

import java.util.Map;

/**
 * Represents a cell that makes up the Board. Used to modify represent players pawns,
 * cards, and or empty cells.
 */
public interface Cell {

  /**
   * Gets the score of a cell represented as a map with a single entry.
   * First integer key represents RED score.
   * Second integer value represents BLUE score.
   * @return a map entry.
   */
  Map.Entry<Integer, Integer> getScore();

  /**
   * Influences the cell to the given influencers color.
   * If cell is empty, add an assoicated pawn and claim ownership.
   * IF cell is pawn and same color, add pawn if less than three pawns exist.
   * If cell is pawn and not same color, change ownership.
   * If cell is card, do nothing.
   * @param influencer color to represent influencer.
   */
  void influence(PlayerColor influencer);


  /**
   * Gets the number of pawns.
   * @returns pawns as integer.
   */
  int getPawns();

  /**
   * Returns a copy of this cell.
   * @return a cell that is a copy.
   */
  Cell copy();

  /**
   * Returns the playercolor of this cell, if there is a player claiming it.
   * @return Playercolor if player is claiming this cell.
   */
  PlayerColor getPlayerColor();

  /**
   * Returns a string representation of this cell.
   * @return a string of this cell.
   */
  String toString();


}
