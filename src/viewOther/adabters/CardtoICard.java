package viewOther.adabters;

import java.util.List;
import java.util.Map;

import model.Card;
import model.PlayerColor;
import viewOther.model.ICard;

/**
 * Adapter to adapt from card to ICard. Used with model adapter.
 */
public class CardtoICard implements ICard, Card {
  Card adaptee;

  /**
   * Card adapter to adapt.
   * @param adaptee to adapt from.
   */
  public CardtoICard(Card adaptee) {
    this.adaptee = adaptee;
  }

  @Override
  public PlayerColor getColor() {
    return this.adaptee.getColor();
  }

  @Override
  public List<Map.Entry<Integer, Integer>> getInfluence() {
    return this.adaptee.getInfluence();
  }

  @Override
  public int getCost() {
    return this.adaptee.getCost();
  }

  @Override
  public String getName() {
    return this.adaptee.getName();
  }

  @Override
  public int getValue() {
    return this.adaptee.getValue();
  }

  @Override
  public int[][] getInfluenceGrid() {
    int[][] influence = new int[5][5];
    for (int row = 0; row < 5; row++) {
      for (int col = 0; col < 5; col++) {
        if (row == 2 && col == 2) {
          influence[row][col] = 2;
        }
        else {
          influence[row][col] = 0;
        }
      }
    }

    for (Map.Entry<Integer, Integer> entry : this.adaptee.getInfluence()) {
      influence[entry.getKey() + 2][entry.getValue() + 2] = 1;
    }
    return influence;
  }
}
