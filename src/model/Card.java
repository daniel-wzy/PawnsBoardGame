package model;

import java.util.List;
import java.util.Map;

/**
 * Represents a card that can be used in a players deck to play pawns board.
 */
public interface Card {
  /**
   * Gets the name of this card.
   * @return a string representing the name of the card.
   */
  String getName();

  /**
   * Gets the cost of this card.
   * @return an int represnting the cost of this card.
   */
  int getCost();

  /**
   * Gets the value of this card.
   * @return an int representing the value of this card.
   */
  int getValue();

  /**
   * Gets the color of this card.
   * @return a color representing the owner of this card.
   */
  PlayerColor getColor();

  /**
   * Gets the influence of a card, that is, which cells relative to the card get pawns
   * added to them.
   * @return hash map of coordinate pairs (Card is at relative cell 0,0).
   */
  List<Map.Entry<Integer, Integer>> getInfluence();

}
