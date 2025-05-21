package model;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

/**
 * Tests for basicpawnsboard.
 */
public class BasicPawnsBoardTest {
  IPawnsBoard exampleBoard;

  // Setup.
  @Before
  public void setUp() {
    exampleBoard = new BasicPawnsBoard("deckConfigFiles"
            + File.separator + "example1.txt",
            "deckConfigFiles"
                    + File.separator + "example1.txt",
            false, 5, new Random(0226), 5, 3);
  }

  // Tests constructor.
  @Test
  public void testConstructor() {
    assertThrows(IllegalArgumentException.class, () -> new BasicPawnsBoard(null,
            null, false, 5, new Random(0226), 5, 3));
    assertThrows(IllegalArgumentException.class, () ->
            new BasicPawnsBoard("deckConfigFiles" +
                    File.separator + "example1.txt", "deckConfigFiles" +
                    File.separator + "example1.txt",
                    false, 0, new Random(0226), 5, 3));
    assertThrows(IllegalArgumentException.class, () ->
            new BasicPawnsBoard("deckConfigFiles" +
                    File.separator + "example1.txt", "deckConfigFiles" +
                    File.separator + "example1.txt",
                    false, 5, new Random(0226), 0, 3));
    assertThrows(IllegalArgumentException.class, () ->
            new BasicPawnsBoard("deckConfigFiles" +
                    File.separator + "example1.txt",
                    "deckConfigFiles" +
                            File.separator + "example1.txt",
                    false, 0, new Random(0226), 5, 0));
  }

  // Tests get turn.
  @Test
  public void testGetTurnAndTurnPass() {
    assertEquals(PlayerColor.RED, exampleBoard.getTurn());
    exampleBoard.pass(PlayerColor.RED);
    exampleBoard.pass(PlayerColor.RED);
    assertEquals(PlayerColor.RED, exampleBoard.getTurn());
    exampleBoard.pass(PlayerColor.BLUE);
    assertEquals(PlayerColor.RED, exampleBoard.getTurn());
  }

  // Tests getScore, getScores, and makeMove method.
  @Test
  public void testScoreing() {
    setUp();
    List<Map.Entry<Integer, Integer>> scoreMap;

    scoreMap = new ArrayList<>();
    assertEquals(Map.entry(0, 0), exampleBoard.getScore());
    scoreMap.add(Map.entry(0, 0));
    scoreMap.add(Map.entry(0, 0));
    scoreMap.add(Map.entry(0, 0));
    assertEquals(scoreMap, exampleBoard.getScores());
    exampleBoard.makeMove(PlayerColor.RED, 0, 0, 0);
    scoreMap = new ArrayList<>();
    assertEquals(Map.entry(0, 0), exampleBoard.getScore());
    scoreMap.add(Map.entry(0, 0));
    scoreMap.add(Map.entry(0, 0));
    scoreMap.add(Map.entry(0, 0));
    assertEquals(scoreMap, exampleBoard.getScores());
    exampleBoard.makeMove(PlayerColor.BLUE, 0, 0, 4);
    assertEquals(PlayerColor.RED, exampleBoard.getTurn());
    exampleBoard.makeMove(PlayerColor.RED, 0, 0, 1);
    exampleBoard.makeMove(PlayerColor.BLUE, 0, 0, 3);
    exampleBoard.makeMove(PlayerColor.RED, 0, 1, 0);
    assertEquals(PlayerColor.RED, exampleBoard.getTurn());
    exampleBoard.makeMove(PlayerColor.BLUE, 0, 1, 4);
    exampleBoard.makeMove(PlayerColor.RED, 0, 2, 0);
    scoreMap = new ArrayList<>();
    assertEquals(Map.entry(0, 0), exampleBoard.getScore());
    scoreMap.add(Map.entry(0, 0));
    scoreMap.add(Map.entry(0, 0));
    scoreMap.add(Map.entry(0, 0));
    assertEquals(scoreMap, exampleBoard.getScores());
    exampleBoard.makeMove(PlayerColor.BLUE, 0, 2, 4);
    exampleBoard.makeMove(PlayerColor.RED, 0, 1, 1);
    exampleBoard.makeMove(PlayerColor.BLUE, 0, 1, 3);
    exampleBoard.pass(PlayerColor.RED);
    assertEquals(PlayerColor.RED, exampleBoard.getTurn());
    exampleBoard.makeMove(PlayerColor.BLUE, 0, 2, 3);
    exampleBoard.pass(PlayerColor.RED);
    exampleBoard.makeMove(PlayerColor.BLUE, 0, 1, 2);
    exampleBoard.makeMove(PlayerColor.BLUE, 0, 0, 2);
    exampleBoard.pass(PlayerColor.RED);
    exampleBoard.makeMove(PlayerColor.BLUE, 0, 2, 2);
    exampleBoard.pass(PlayerColor.RED);
    exampleBoard.makeMove(PlayerColor.BLUE, 0, 2, 1);
    exampleBoard.pass(PlayerColor.RED);
    exampleBoard.makeMove(PlayerColor.BLUE, 0, 0, 2);
    scoreMap = new ArrayList<>();
    assertEquals(Map.entry(0, 0), exampleBoard.getScore());
    scoreMap.add(Map.entry(0, 0));
    scoreMap.add(Map.entry(0, 0));
    scoreMap.add(Map.entry(0, 0));
    assertEquals(scoreMap, exampleBoard.getScores());
  }

  // Tests the getBoard method.
  @Test
  public void testGetBoard() {
    setUp();
    List<List<Cell>> board = exampleBoard.getBoard();
    assertEquals("_*_R", board.get(0).get(0).toString());
    board.get(0).set(0, null);
    board = exampleBoard.getBoard();
    assertEquals("_*_R", board.get(0).get(0).toString());
    exampleBoard.makeMove(PlayerColor.RED, 0, 0, 0);
    board = exampleBoard.getBoard();
    assertEquals("_*_R", board.get(0).get(0).toString());
  }

  // Tests the getHand method
  @Test
  public void testGetHand() {
    setUp();
    assertEquals(0, exampleBoard.getRedHand().size());
    assertEquals(0, exampleBoard.getBlueHand().size());
    exampleBoard.makeMove(PlayerColor.RED, 0, 0, 0);
    assertEquals(0, exampleBoard.getRedHand().size());
    assertEquals(0, exampleBoard.getBlueHand().size());
  }

  // Tests the card drawing functionaltiy.
  @Test
  public void testDraw() {
    IPawnsBoard smallExample =
            new BasicPawnsBoard("deckConfigFiles"
                    + File.separator + "smallExample.txt",
                    "deckConfigFiles"
                            + File.separator + "smallExample.txt",
                    false, 5, new Random(0226), 5, 3);

    assertEquals(0, smallExample.getRedHand().size());
    assertEquals(0, smallExample.getBlueHand().size());
    smallExample.makeMove(PlayerColor.RED, 0, 0, 0);
    assertEquals(0, smallExample.getRedHand().size());
    assertEquals(0, smallExample.getBlueHand().size());
  }

  // Test consecutive tracker and game over.
  @Test
  public void testConsecuitveAndGameOver() {
    setUp();
    assertEquals(false, exampleBoard.isGameOver());
    exampleBoard.pass(PlayerColor.RED);
    assertEquals(false, exampleBoard.isGameOver());
    exampleBoard.pass(PlayerColor.BLUE);
    assertEquals(false, exampleBoard.isGameOver());
  }


}
