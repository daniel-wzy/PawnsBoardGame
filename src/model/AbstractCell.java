package model;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a cell on the board and the operations that are used
 * to manipulate the cells.
 */
abstract class AbstractCell implements Cell {
  PlayerColor owner;

  /**
   * Creates an owner less cell that can be influenced.
   */
  public AbstractCell() {
    this.owner = null;
  }

  @Override
  public Map.Entry<Integer, Integer> getScore() {
    Map<Integer, Integer> scores = new HashMap<>();
    scores.put(0, 0);
    return scores.entrySet().iterator().next();
  }

  @Override
  public int getPawns() {
    return 0;
  }

  @Override
  public PlayerColor getPlayerColor() {
    return this.owner;
  }

}
