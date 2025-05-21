package model;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

/**
 * Represnts a basic card. Cards influence is represented by a list of cordinate pairs
 * with the card being at 0,0, going by row, col.
 * For example, a card that puts a pawn to the left of itself,
 * would have an influence in cell 0, -1.
 */
public class BasicCard extends AbstractCell implements Card {
  private final String name;
  private final int cost;
  private final int value;
  private final List<Map.Entry<Integer, Integer>> influence;
  private final PlayerColor color;
  /*
   * !!! INVARIANT !!!
   * Cost and value must be greater or equal to 0.
   * - Preserved by the constructor.
   * - Preserved by methods as is private final.
   * - Always evaluates to true.
   */

  /**
   * Creates a basic card with given parameters.
   * @param name name of card.
   * @param cost cost of card.
   * @param value value of card.
   * @param influence influence of card.
   * @throws IllegalArgumentException if the name or influence map are null. Or if cost or value
   *     are negative.
   */
  public BasicCard(String name,
                   int cost,
                   int value,
                   List<Map.Entry<Integer, Integer>> influence, PlayerColor color) {
    super();
    if (name == null || influence == null) {
      throw new IllegalArgumentException("Name or Influence cannot be null!");
    }
    else if (cost < 0 || value < 0) {
      throw new IllegalArgumentException("Cost or Value cannot be negative!");
    }
    this.name = name;
    this.cost = cost;
    this.value = value;
    this.influence = influence;
    this.color = color;
  }

  /**
   * Gets the name of this card.
   * @return a string representing the name.
   */
  @Override
  public String getName() {
    return this.name;
  }

  /**
   * Gets the cost of this card.
   * @return the cost of this card as int.
   */
  @Override
  public int getCost() {
    return this.cost;
  }

  /**
   * Gets the value of this card.
   * @return gets the value of this card as a int.
   */
  @Override
  public int getValue() {
    return this.value;
  }

  @Override
  public PlayerColor getColor() {
    return this.color;
  }

  /**
   * Returns a copy of this influence map.
   * @return hash map of this cards influence.
   */
  @Override
  public List<Map.Entry<Integer, Integer>> getInfluence() {
    List<Map.Entry<Integer, Integer>> copy = new ArrayList<>();
    for (Map.Entry<Integer, Integer> entry : this.influence) {
      copy.add(entry);
    }
    return copy;
  }

  @Override
  public Map.Entry<Integer, Integer> getScore() {
    Map<Integer, Integer> scores = new HashMap<>();
    if (this.color == PlayerColor.RED) {
      scores.put(this.value, 0);
    }
    else if (this.color == PlayerColor.BLUE) {
      scores.put(0, this.value);
    }
    return scores.entrySet().iterator().next();
  }

  @Override
  public void influence(PlayerColor influencer) {
    // Does nothing if influence is on anything but pawns.
  }

  @Override
  public Cell copy() {
    return new BasicCard(this.name, this.cost, this.value, this.influence, this.color);
  }

  @Override
  public String toString() {
    String color;
    if (this.color == PlayerColor.RED) {
      color = "R";
    }
    else {
      color = "B";
    }
    return String.format("%03d", this.value) + color;
  }

  @Override
  public PlayerColor getPlayerColor() {
    return this.color;
  }

}
