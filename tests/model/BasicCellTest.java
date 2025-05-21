package model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Represents tests for basic cell.
 */
public class BasicCellTest {
  Cell startCell;
  Cell startCard;

  // Sets up intial variables.
  @Before
  public void setUp() {
    startCell = new BasicPawn();
    startCard = new BasicCard("Card Test", 2, 3,
            new ArrayList<>(), PlayerColor.RED);
  }

  // Tests basic cell constructor.
  @Test
  public void testBasicCellConstructor() {
    setUp();
    Map<Integer, Integer> pawnScore = new HashMap();
    pawnScore.put(0,0);
    assertEquals(pawnScore.entrySet().iterator().next(), this.startCell.getScore());
    assertEquals("___", this.startCell.toString());
    assertEquals(0, this.startCell.getPawns());
  }

  // Tests a cell functionality as a cell.
  @Test
  public void testCellInfluencePawnsString() {
    setUp();
    assertEquals(0, this.startCell.getPawns());
    assertEquals("___", this.startCell.toString());
    this.startCell.influence(PlayerColor.RED);
    assertEquals(1, this.startCell.getPawns());
    assertEquals("_*_R", this.startCell.toString());
    this.startCell.influence(PlayerColor.RED);
    assertEquals(2, this.startCell.getPawns());
    assertEquals("*_*R", this.startCell.toString());
    this.startCell.influence(PlayerColor.BLUE);
    assertEquals(2, this.startCell.getPawns());
    assertEquals("*_*B", this.startCell.toString());
    this.startCell.influence(PlayerColor.RED);
    assertEquals(2, this.startCell.getPawns());
    assertEquals("*_*R", this.startCell.toString());
    this.startCell.influence(PlayerColor.RED);
    assertEquals(3, this.startCell.getPawns());
    assertEquals("***R", this.startCell.toString());
    this.startCell.influence(PlayerColor.RED);
    assertEquals(3, this.startCell.getPawns());
    assertEquals("***R", this.startCell.toString());
    this.startCell.influence(PlayerColor.BLUE);
    assertEquals(3, this.startCell.getPawns());
    assertEquals("***B", this.startCell.toString());
  }

  // Tests card functinality as a cell.
  @Test
  public void testCardAsCell() {
    setUp();
    Map<Integer, Integer> cardScore = new HashMap();
    cardScore.put(3,0);
    assertEquals(cardScore.entrySet().iterator().next(), this.startCard.getScore());
    assertEquals("003R", this.startCard.toString());
    Cell blueCard = new BasicCard("Card Test", 2, 3, new ArrayList<>(), PlayerColor.BLUE);
    Map<Integer, Integer> blueCardScore = new HashMap();
    blueCardScore.put(0,3);
    assertEquals(blueCardScore.entrySet().iterator().next(), blueCard.getScore());
    assertEquals("003B", blueCard.toString());
  }
}
