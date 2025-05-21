package strategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import model.Card;
import model.Player;
import model.PlayerColor;
import model.BasicCard;

/**
 * A simple test player that always returns a fixed hand.
 */
public class MockPlayer implements Player {
  private PlayerColor color;
  private List<Card> hand;

  /**
   * Construct a MockPlayer.
   *
   * @param color The color of the player
   */
  public MockPlayer(PlayerColor color) {
    this.color = color;
    this.hand = new ArrayList<>();
    List<Map.Entry<Integer, Integer>> influence = new ArrayList<>();
    this.hand.add(new BasicCard("TestCard", 1, 1, influence, color));
  }

  @Override
  public PlayerColor getPlayerColor() {
    return color;
  }

  @Override
  public List<Card> getHand() {
    return hand;
  }

  @Override
  public void takeCard(int handIndex) {
    throw new IllegalArgumentException("takeCard not supported in MockPlayer");
  }

  @Override
  public int getRemainingDeckCards() {
    return 0;
  }

  @Override
  public int getHandSize() {
    return hand.size();
  }

  @Override
  public MockPlayer copy() {
    MockPlayer copy = new MockPlayer(this.color);
    copy.hand = new ArrayList<>(this.hand);
    return copy;
  }
}