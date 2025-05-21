package model;

import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

/**
 * Tests for the basic player class.
 */
public class BasicPlayerTest {
  Player newPlayer;
  Player smallDeckPlayer;

  String data = "Security 1 6\n" +
          "XXXXX\n" +
          "XXIXX\n" +
          "XICIX\n" +
          "XXIXX\n" +
          "XXXXX\n" +
          "Bee 1 1\n" +
          "XXIXX\n" +
          "XXXXX\n" +
          "XXCXX\n" +
          "XXXXX\n" +
          "XXIXX\n" +
          "Line 2 3\n" +
          "XXXXX\n" +
          "XIXIX\n" +
          "XICIX\n" +
          "XIXIX\n" +
          "XXXXX\n" +
          "Box 1 5\n" +
          "XXXXX\n" +
          "XIIIX\n" +
          "XICIX\n" +
          "XIIIX\n" +
          "XXXXX\n" +
          "Triangle 1 5\n" +
          "XXXXX\n" +
          "XXIXX\n" +
          "XICIX\n" +
          "XIIIX\n" +
          "XXXXX\n" +
          "Point 1 5\n" +
          "IXXXI\n" +
          "XXXXX\n" +
          "IXCXI\n" +
          "XXXXX\n" +
          "IXXXI\n" +
          "Crossfire 1 6\n" +
          "XXIXX\n" +
          "XIXIX\n" +
          "IICII\n" +
          "XIXIX\n" +
          "XXIXX\n" +
          "Crown 1 8\n" +
          "XIXIX\n" +
          "XIXIX\n" +
          "IICII\n" +
          "XIXIX\n" +
          "XIXIX\n" +
          "Star 1 7\n" +
          "XIXIX\n" +
          "XIIIX\n" +
          "IICII\n" +
          "XIIIX\n" +
          "XIXIX\n" +
          "Diamond 1 4\n" +
          "XXCXX\n" +
          "XIXIX\n" +
          "XIXIX\n" +
          "XIXIX\n" +
          "XXCXX\n" +
          "Wheel 1 9\n" +
          "XIXIX\n" +
          "IXCXI\n" +
          "IICII\n" +
          "IXCXI\n" +
          "XIXIX\n" +
          "Shield 1 6\n" +
          "XXXIX\n" +
          "XIXIX\n" +
          "IICII\n" +
          "XIXIX\n" +
          "XXXIX\n" +
          "Axe 1 7\n" +
          "XIXIX\n" +
          "XXIXX\n" +
          "IXCXI\n" +
          "XXIXX\n" +
          "XIXIX\n" +
          "Anchor 1 5\n" +
          "XXIXX\n" +
          "XIXIX\n" +
          "IICII\n" +
          "XIXIX\n" +
          "XXIXX\n" +
          "Wave 1 6\n" +
          "XXIXX\n" +
          "IXCIX\n" +
          "IIXII\n" +
          "IXCIX\n" +
          "XXIXX\n" +
          "Hex 1 10\n" +
          "XIIIX\n" +
          "IXCXI\n" +
          "IICII\n" +
          "IXCXI\n" +
          "XIIIX";

  /**
   * Inits players.
   */
  @Before
  public void setUp() {
    newPlayer = new BasicPlayer(PlayerColor.BLUE, 4, new Random(0226), false,
            data);
    smallDeckPlayer = new BasicPlayer(PlayerColor.BLUE, 4,
            new Random(0226), false,
            data);
  }

  // Tests constructor for player.
  @Test
  public void testPlayerConstructor() {
    assertThrows(IllegalArgumentException.class, () ->
            new BasicPlayer(null, 4, new Random(0226), false,
                    data));
    assertThrows(IllegalArgumentException.class, () ->
            new BasicPlayer(PlayerColor.RED, 0, new Random(0226), false,
                    data));
    assertThrows(IllegalArgumentException.class, () ->
            new BasicPlayer(PlayerColor.RED, 4, new Random(0226), false,
                    null));

    assertThrows(IllegalArgumentException.class, () ->
            new BasicPlayer(null, 4, new Random(),
                    data));
    assertThrows(IllegalArgumentException.class, () ->
            new BasicPlayer(PlayerColor.RED, 0, new Random(),
                    data));
    assertThrows(IllegalArgumentException.class, () ->
            new BasicPlayer(PlayerColor.RED, 4, new Random(),
                    null));
    assertThrows(IllegalArgumentException.class, () ->
            new BasicPlayer(PlayerColor.RED, 4, null,
                    data));

    Player testPlayer = new BasicPlayer(PlayerColor.RED, 4,
            new Random(0226), false, data);
    assertEquals(4, testPlayer.getHandSize());
    assertEquals(false,
            testPlayer.getHand().get(0).getInfluence() ==
                    newPlayer.getHand().get(0).getInfluence());
    List<Map.Entry<Integer, Integer>> normal = new ArrayList<>();
    normal.add(Map.entry(-1, 0));
    normal.add(Map.entry(0, -1));
    normal.add(Map.entry(0, 1));
    normal.add(Map.entry(1, 0));
    List<Map.Entry<Integer, Integer>> inverted = new ArrayList<>();
    inverted.add(Map.entry(-1, 0));
    inverted.add(Map.entry(0, 1));
    inverted.add(Map.entry(0, -1));
    inverted.add(Map.entry(1, 0));
    assertEquals(normal, testPlayer.getHand().get(0).getInfluence());
    assertEquals(inverted, newPlayer.getHand().get(0).getInfluence());
    assertEquals(12, testPlayer.getRemainingDeckCards());
  }

  // Tests the get hand method.
  @Test
  public void getHand() {
    setUp();
    List<Card> hand = newPlayer.getHand();
    assertEquals(4, hand.size());
    List<Card> smallHand = smallDeckPlayer.getHand();
    assertEquals(4, smallHand.size());
  }

  // Tests the take card method.
  @Test
  public void takeCard() {
    setUp();
    List<Card> hand = newPlayer.getHand();
    int deckSize = newPlayer.getRemainingDeckCards();
    assertEquals(4, hand.size());
    assertEquals(12, deckSize);

    assertThrows(IllegalArgumentException.class, () -> newPlayer.takeCard(4));
    assertThrows(IllegalArgumentException.class, () -> newPlayer.takeCard(-1));
    newPlayer.takeCard(0);
    hand = newPlayer.getHand();
    deckSize = newPlayer.getRemainingDeckCards();
    assertEquals(4, hand.size());
    assertEquals(11, deckSize);
  }

  // Tests the get remaining deck cards method.
  @Test
  public void getRemainingDeckCards() {
    setUp();
    assertEquals(4, newPlayer.getHand().size());
    assertEquals(12, newPlayer.getRemainingDeckCards());
    newPlayer.takeCard(0);
    assertEquals(4, newPlayer.getHand().size());
    assertEquals(11, newPlayer.getRemainingDeckCards());
  }

  // Tests the get handsize method.
  @Test
  public void getHandSize() {
    setUp();
    assertEquals(4, newPlayer.getHand().size());
    assertEquals(4, newPlayer.getHandSize());
    assertEquals(12, newPlayer.getRemainingDeckCards());
    newPlayer.takeCard(0);
    assertEquals(4, newPlayer.getHand().size());
    assertEquals(4, newPlayer.getHandSize());
    assertEquals(11, newPlayer.getRemainingDeckCards());
    newPlayer.takeCard(0);
    assertEquals(4, newPlayer.getHand().size());
    assertEquals(4, newPlayer.getHandSize());
    assertEquals(10, newPlayer.getRemainingDeckCards());
    newPlayer.takeCard(0);
    assertEquals(4, newPlayer.getHand().size());
    assertEquals(4, newPlayer.getHandSize());
    assertEquals(9, newPlayer.getRemainingDeckCards());
  }
}