package model;

import java.io.File;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Represents a basic player that can either be red or blue. Used to facilitate
 * card dealing and managment.
 */
public class BasicPlayer implements Player {
  private List<Card> deck;
  private List<Card> hand;
  private final int handSize;
  private final Random rand;
  private final PlayerColor color;
  private final boolean shuffle;

  /**
   * Creates a new player of given color.
   * @param color color to create player for.
   * @throws IllegalArgumentException if color is null or handsize is less
   *     than 1, or if filepath is null or incorrect.
   */
  public BasicPlayer(PlayerColor color, int handSize, Random seededRandom,
                     boolean shuffle, String deckFile) {
    if (color == null || handSize < 1 || deckFile == null) {
      throw new IllegalArgumentException("One or more arguments are null or less" +
              "than 1!");
    }
    this.deck = new ArrayList<>();
    this.hand = new ArrayList<>();
    this.rand = seededRandom;
    this.color = color;
    this.shuffle = shuffle;
    this.handSize = handSize;
    this.makeNewDeck(deckFile);
  }

  /**
   * Creates a new player with a custom seed.
   * @param color color to create player for.
   * @param seededRandom seed to use for card shuffling.
   * @throws IllegalArgumentException if color is null, randomseed is null, or handsize is less
   *     than 1 or if filepath is null or incorrect.
   */
  public BasicPlayer(PlayerColor color, int handSize, Random seededRandom, String deckFile) {
    if (color == null || handSize < 1 || seededRandom == null || deckFile == null) {
      throw new IllegalArgumentException("One or more arguments are null or less" +
              "than 1!");
    }
    this.deck = new ArrayList<>();
    this.hand = new ArrayList<>();
    this.rand = seededRandom;
    this.color = color;
    this.shuffle = true;
    this.handSize = handSize;
    this.makeNewDeck(deckFile);
  }

  /**
   * Gets a copy of the current hand.
   * @return a copy of the current hand.
   */
  @Override
  public List<Card> getHand() {
    List<Card> handCopy = new ArrayList<>();
    for (Card card : hand) {
      handCopy.add(card);
    }
    return handCopy;
  }

  /**
   * Removes a card from the given index, drawing a new card to take its place.
   * @param handIndex to draw from.
   * @throws IllegalArgumentException if index is invalid.
   */
  @Override
  public void takeCard(int handIndex) {
    if (handIndex < 0 || handIndex >= this.hand.size()) {
      throw new IllegalArgumentException("Invalid hand index!");
    }
    else {
      this.hand.remove(handIndex);
      if (!this.deck.isEmpty()) {
        this.drawFromDeck();
      }
    }
  }

  /**
   * Gets the number of card remaining in deck.
   * @return an int representing deck size.
   */
  @Override
  public int getRemainingDeckCards() {
    return this.deck.size();
  }

  /**
   * Returns the specified handsize for this player. Not actual size of hand.
   * @return an int representing size of hand.
   */
  @Override
  public int getHandSize() {
    return this.handSize;
  }

  @Override
  public PlayerColor getPlayerColor() {
    return this.color;
  }

  /**
   * Creates a new deck from a specified deckfile path. Flips all cards if this player is blue.
   * @param data to use for deck.
   * @throws IllegalArgumentException if there is an issue with reading file structure OR
   *     there is an IO error.
   */
  private void makeNewDeck(String data) {
    String[] lines;
    this.hand.clear();
    this.deck.clear();
    lines = data.split("\n");

    for (int cardStartingIndex = 0;
         cardStartingIndex < Math.floor(lines.length / 6); cardStartingIndex++) {
      try {
        String name = "";
        int cost = 0;
        int value = 0;
        List<Map.Entry<Integer, Integer>> influence = new ArrayList<>();
        String[] basicInfo = lines[cardStartingIndex * 6].split(" ");
        name = basicInfo[0];
        cost = Integer.parseInt(basicInfo[1].trim());
        value = Integer.parseInt(basicInfo[2].trim());
        mapHelper(influence, lines[cardStartingIndex * 6 + 1], -2);
        mapHelper(influence, lines[cardStartingIndex * 6 + 2], -1);
        mapHelper(influence, lines[cardStartingIndex * 6 + 3], 0);
        mapHelper(influence, lines[cardStartingIndex * 6 + 4], 1);
        mapHelper(influence, lines[cardStartingIndex * 6 + 5], 2);
        Card cardToAdd = new BasicCard(name, cost, value, influence, this.color);
        this.deck.add(cardToAdd);
      }

      catch (Exception e) {
        throw new IllegalArgumentException("Error with parsing data!");
      }
    }
    for (int drawCount = 0; drawCount < this.handSize; drawCount++) {
      if (!this.deck.isEmpty()) {
        this.drawFromDeck();
      }
    }
  }

  /**
   * Helper for reading deckfiles. Converts string influence row entries into hashmap entries.
   * Flips card if this player is blue.
   * @param influence map to add to.
   * @param row string row to convert.
   * @param rowIndex row to start at.
   */
  private void mapHelper(List<Map.Entry<Integer,
          Integer>> influence, String row, int rowIndex) {
    String[] chr = row.split("");
    try {
      for (int column = 0; column < row.length(); column++) {
        if (chr[column].equals("I")) {
          int correctColumn;
          if (this.color == PlayerColor.RED) {
            correctColumn = column - 2;
          }
          else {
            correctColumn = (column - 2) * -1;
          }
          Map<Integer, Integer> influenceMap = new HashMap<>();
          influenceMap.put(rowIndex, correctColumn);
          influence.add(influenceMap.entrySet().iterator().next());
        }
      }
    }
    catch (Exception e) {
      throw new IllegalStateException("Error Parsing Rows!");
    }
  }

  /**
   * Draws a card form deck and inserts it into the hand.
   * Does not throw a card if nothing is in deck.
   */
  void drawFromDeck() {
    if (this.deck.size() > 0) {
      int index;
      if (this.shuffle) {
        index = this.rand.nextInt(deck.size());
      }
      else {
        index = 0;
      }
      Card card = this.deck.get(index);
      this.hand.add(card);
      this.deck.remove(index);
    }
  }

  /**
   * Returns a immutable copy of the players.
   *
   * @return a copy of the players
   */
  @Override
  public Player copy() {
    BasicPlayer copy = new BasicPlayer(this.color, this.handSize, new Random(),
            "deckConfigFiles" + File.separator + "example1.txt");
    copy.deck = new ArrayList<>(this.deck);
    copy.hand = new ArrayList<>(this.hand);
    return copy;
  }
}
