package strategy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Map;
import java.util.Random;

import model.BasicPawnsBoard;
import model.BasicPlayer;
import model.IPawnsBoard;
import model.PlayerColor;

import org.junit.Before;
import org.junit.Test;

/**
 * Test class for strategies FillFirst and MaximizeRowScore.
 */
public class StrategyTest {

  private final String deckFile = "deckConfigFiles" + File.separator + "example1.txt";

  private BasicPawnsBoard board;
  private BasicPlayer redPlayer;
  private PawnsBoardStrategy fillFirst;
  private PawnsBoardStrategy maximizeRowScore;

  @Before
  public void setUp() {
    this.board = new BasicPawnsBoard(deckFile, deckFile, false, 5,
            new Random(42), 5, 3);
    this.redPlayer = new BasicPlayer(PlayerColor.RED, 5, new Random(42),
            false, deckFile);
    this.fillFirst = new FillFirst();
    this.maximizeRowScore = new MaximizeRowScore();

    assertEquals(PlayerColor.RED, board.getTurn());
  }

  @Test
  public void testFillFirstReturnsValidMove() {
    Move move = fillFirst.chooseMove(board, redPlayer);
    assertNotNull(move);
    assertTrue(board.isValid(redPlayer.getPlayerColor(), move.handindex, move.r, move.c));
  }

  @Test
  public void testMaximizeRowScoreReturnsValidMoveWhenImprovementPossible() {
    Move move = maximizeRowScore.chooseMove(board, redPlayer);
    assertNotNull(move);

    IPawnsBoard boardCopy = board.copy();
    boardCopy.makeMove(redPlayer.getPlayerColor(), move.handindex, move.r, move.c);

    Map.Entry<Integer, Integer> rowScore = boardCopy.getScores().get(move.r);
    int redScore;
    int blueScore;
    if (redPlayer.getPlayerColor() == PlayerColor.RED) {
      redScore = rowScore.getKey();
      blueScore = rowScore.getValue();
    } else {
      redScore = rowScore.getValue();
      blueScore = rowScore.getKey();
    }
    assertTrue(redScore > blueScore);
  }

  @Test
  public void testMaximizeRowScoreReturnsNullWhenNoImprovementPossible() {
    setUp();
    BasicPawnsBoard localBoard = new BasicPawnsBoard(deckFile, deckFile, false, 5,
            new Random(42), 5, 3);
    BasicPlayer localRed = new BasicPlayer(PlayerColor.RED, 5, new Random(42),
            false, deckFile);

    for (int r = 0; r < localBoard.getHeight(); r++) {
      if (localBoard.isValid(PlayerColor.BLUE, 0, r, localBoard.getWidth() - 1)) {
        localBoard.pass(PlayerColor.RED);
        localBoard.makeMove(PlayerColor.BLUE, 0, r, localBoard.getWidth() - 1);
      }
    }
    localBoard.pass(PlayerColor.RED);
    localBoard.makeMove(PlayerColor.BLUE, 0, 2, localBoard.getWidth() - 1);
    localBoard.makeMove(PlayerColor.RED, 0, 0, 0);
    localBoard.makeMove(PlayerColor.BLUE, 0, 0, localBoard.getWidth() - 2);
    localBoard.pass(PlayerColor.RED);
    localBoard.makeMove(PlayerColor.BLUE, 0, 1, localBoard.getWidth() - 2);

    Move move = maximizeRowScore.chooseMove(localBoard, localRed);
    assertNull(move);
  }
}