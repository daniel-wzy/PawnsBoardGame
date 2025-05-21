package model;

import java.util.List;

/**
 * Represents a player used to keep track of cards. (Deck and hand).
 */
public interface Player {

  /**
   * Gets the current hand of a player.
   * @return an array list of the hand of a player.
   */
  List<Card> getHand();

  /**
   * Removes a card from the given index, drawing a new card to take its place.
   * @param handIndex to draw from.
   * @throws IllegalArgumentException if index is invalid.
   */
  void takeCard(int handIndex);

  /**
   * Returns the number of remaining cards in the deck. Excluding the hand.
   * @return int reprsenting the remainng cards.
   */
  int getRemainingDeckCards();

  /**
   * Returns the specified size of hand, not actualy hand size.
   * @return an integer represnting hand size.
   */
  int getHandSize();

  /**
   * Returns the color of the given player.
   * @return the color of the specific player.
   */
  PlayerColor getPlayerColor();

  /**
   * Returns a immutable copy of the players.
   *
   * @return a copy of the players
   */
  Player copy();

}
