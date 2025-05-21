package model;

import org.junit.Before;
import org.junit.Test;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

/**
 * Tests for basic board.
 */
public class BasicBoardTest {

  private BasicBoard board;

  /**
   * Initializes board with given dimensions.
   */
  @Before
  public void setUp() {
    board = new BasicBoard(5, 5);
  }

  // Tests if the board is initialized correctly.
  @Test
  public void testInitialization() {
    assertNotNull(board.getBoard());
    assertEquals(5, board.getBoard().size());
    assertEquals(5, board.getBoard().get(0).size());
    assertEquals(5, board.getWidth());
    assertEquals(5, board.getHeight());
  }

  // Tests if a cell is retrieved correctly.
  @Test
  public void testGetCell() {
    Card card = new BasicCard("Test Card", 1, 1, new ArrayList<>(), PlayerColor.RED);
    board.placeCard(0, 0, card, PlayerColor.RED);

    Cell cell = board.getCell(0, 0);

    // Check that the copied cell has the same properties as the original cell
    assertNotSame(board.getCell(0, 0), cell);
    assertEquals(board.getCell(0, 0).toString(), cell.toString());
  }

  // Tests if Illegal Argument Exception is thrown when the card is placed on an invalid position.
  @Test
  public void testInvalidCardPlacement() {
    Card card = new BasicCard("Test Card", 1, 1,
            new ArrayList<>(), PlayerColor.RED);

    assertThrows(IndexOutOfBoundsException.class, () -> board.placeCard(-1,
            -1, card, PlayerColor.RED));
  }

  // Tests if isValidPosition returns true if basic pawn is placed at a valid position.
  @Test
  public void testIsValidPositionPawn_valid() {
    Cell target = new BasicPawn(PlayerColor.RED, 2);
    assertTrue(board.isValidPosition(2, 0, PlayerColor.RED, target));
  }

  // Tests if isValidPosition returns false if basic pawn is placed at an invalid position.
  @Test
  public void testIsValidPositionPawn_invalid() {
    Cell target = new BasicPawn(PlayerColor.RED, 4);
    assertFalse(board.isValidPosition(2, 2, PlayerColor.RED, target));
  }

  // Tests if the right scores are retrieved.
  @Test
  public void testGetScores() {
    // Create cards to place
    Card redCard = new BasicCard("Red Card", 1, 1,
            new ArrayList<>(), PlayerColor.RED);
    Card blueCard = new BasicCard("Blue Card", 1, 1,
            new ArrayList<>(), PlayerColor.BLUE);

    board.placeCard(0, 0, redCard, PlayerColor.RED);
    board.placeCard(1, 4, blueCard, PlayerColor.BLUE);

    List<Map.Entry<Integer, Integer>> scores = board.getScores();

    // Check if the scores for red and blue are correct
    assertEquals((Integer) 1, scores.get(0).getKey());  
    assertEquals((Integer) 0, scores.get(0).getValue());
  }

  // Tests if the card placed influences the surrounding cells
  @Test
  public void testInfluenceHelper() {
    // Define influence for the card
    List<Map.Entry<Integer, Integer>> influence = new ArrayList<>();
    influence.add(new AbstractMap.SimpleEntry<>(0, -1));
    influence.add(new AbstractMap.SimpleEntry<>(0, 1));

    Card card = new BasicCard("Test Card", 1, 1, influence, PlayerColor.RED);

    board.placeCard(1, 0, card, PlayerColor.RED);

    Cell leftCell = board.getCell(1, 0);
    Cell rightCell = board.getCell(2, 0);

    assertEquals(PlayerColor.RED, leftCell.getPlayerColor());
    assertEquals(PlayerColor.RED, rightCell.getPlayerColor());
  }
}
