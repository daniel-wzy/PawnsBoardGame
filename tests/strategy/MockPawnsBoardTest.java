package strategy;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import model.IPawnsBoard;
import model.PlayerColor;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the mock model.
 */
public class MockPawnsBoardTest {
  private StringBuilder log;
  private MockPawnsBoard mockBoard;
  private MockPlayer testPlayer;
  private PawnsBoardStrategy fillFirst;
  private PawnsBoardStrategy maximizeRowScore;

  @Before
  public void setUp() {
    log = new StringBuilder();
    mockBoard = new MockPawnsBoard(log, 3, 2);
    testPlayer = new MockPlayer(PlayerColor.RED);
    fillFirst = new FillFirst();
    maximizeRowScore = new MaximizeRowScore();
  }

  @Test
  public void testFillFirstTranscript() {
    Move move = fillFirst.chooseMove(mockBoard, testPlayer);
    String transcript = log.toString();
    assertTrue(transcript.contains("isValid called for (0, 0)"));
    assertNotNull(move);
  }

  @Test
  public void testMaximizeRowScore() {
    mockBoard.getScores().set(0, new java.util.AbstractMap.SimpleEntry<>(0, 1));
    Move move = maximizeRowScore.chooseMove(mockBoard, testPlayer);
    String transcript = log.toString();
    assertTrue(transcript.contains("getScores called"));
    assertNotNull(move);

    IPawnsBoard copy = mockBoard.copy();
    copy.makeMove(testPlayer.getPlayerColor(), move.handindex, move.r, move.c);
    Map.Entry<Integer, Integer> rowScore = copy.getScores().get(move.r);

    int redScore;
    int blueScore;
    if (testPlayer.getPlayerColor() == PlayerColor.RED) {
      redScore = rowScore.getKey();
      blueScore = rowScore.getValue();
    } else {
      redScore = rowScore.getValue();
      blueScore = rowScore.getKey();
    }
    assertTrue(redScore > blueScore);
  }
}