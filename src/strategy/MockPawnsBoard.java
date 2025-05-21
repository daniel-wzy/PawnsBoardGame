package strategy;

import java.awt.Point;
import java.util.ArrayList;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

import controller.ModelActions;
import model.Card;
import model.Cell;
import model.IPawnsBoard;
import model.Player;
import model.PlayerColor;
import model.BasicPawn;

/**
 * A simple mock implementation of the IPawnsBoard interface for testing strategies.
 */
public class MockPawnsBoard implements IPawnsBoard {
  public StringBuilder log;  // transcript log
  private int width;
  private int height;
  private List<List<Cell>> board;
  private List<Map.Entry<Integer, Integer>> scores;
  private PlayerColor turn;

  /**
   * Constructs a mock model of the game for testing purposes.
   *
   * @param log the log of the mock
   * @param width the width of the board
   * @param height the height of the board
   */
  public MockPawnsBoard(StringBuilder log, int width, int height) {
    this.log = log;
    this.width = width;
    this.height = height;
    this.turn = PlayerColor.RED;

    board = new ArrayList<>();
    for (int i = 0; i < height; i++) {
      List<Cell> row = new ArrayList<>();
      for (int j = 0; j < width; j++) {
        row.add(new BasicPawn(PlayerColor.RED, 3));
      }
      board.add(row);
    }

    scores = new ArrayList<>();
    for (int i = 0; i < height; i++) {
      scores.add(new AbstractMap.SimpleEntry<>(0, 0));
    }
  }

  @Override
  public PlayerColor getTurn() {
    log.append("getTurn called\n");
    return turn;
  }

  @Override
  public List<Card> getRedHand() {
    return null;
  }

  @Override
  public List<Card> getBlueHand() {
    return null;
  }

  @Override
  public boolean isGameOver() {
    return false;
  }

  @Override
  public List<Map.Entry<Integer, Integer>> getScores() {
    log.append("getScores called\n");
    return new ArrayList<>(scores);
  }

  @Override
  public int getHeight() {
    log.append("getHeight called\n");
    return height;
  }

  @Override
  public int getWidth() {
    log.append("getWidth called\n");
    return width;
  }

  @Override
  public List<List<Cell>> getBoard() {
    log.append("getBoard called\n");
    return board;
  }

  @Override
  public boolean isValid(PlayerColor color, int handIndex, int row, int col) {
    log.append("isValid called for (" + row + ", " + col + ") handIndex:" + handIndex + "\n");
    return true;
  }

  @Override
  public void makeMove(PlayerColor color, int handIndex, int row, int col) {
    log.append("makeMove called for (" + row + ", " + col + ") handIndex:" + handIndex + "\n");
    Map.Entry<Integer, Integer> entry = scores.get(row);
    if (color == PlayerColor.RED) {
      scores.set(row, new AbstractMap.SimpleEntry<>(entry.getKey() + 1, entry.getValue()));
    } else {
      scores.set(row, new AbstractMap.SimpleEntry<>(entry.getKey(), entry.getValue() + 1));
    }
  }

  @Override
  public void pass(PlayerColor color) {
    log.append("pass called for " + color + "\n");
  }

  @Override
  public Map.Entry<Integer, Integer> getScore() {
    log.append("getScore called\n");
    int redTotal = 0;
    int blueTotal = 0;
    for (Map.Entry<Integer, Integer> entry : scores) {
      if (entry.getKey() > entry.getValue()) {
        redTotal += entry.getKey();
      } else if (entry.getValue() > entry.getKey()) {
        blueTotal += entry.getValue();
      }
    }
    return new AbstractMap.SimpleEntry<>(redTotal, blueTotal);
  }

  @Override
  public Cell getCell(int r, int c) {
    log.append("getCell called for (" + r + ", " + c + ")\n");
    return board.get(r).get(c);
  }

  @Override
  public int getHandHighlighted(PlayerColor color) {
    log.append("getHandHighlighted called for " + color + "\n");
    return -1;
  }

  @Override
  public int setHandHighlighted(int handHighlighted, PlayerColor color) {
    log.append("setHandHighlighted called with " + handHighlighted + " for " + color + "\n");
    return handHighlighted;
  }

  @Override
  public Point getCellHighlighted(PlayerColor color) {
    log.append("getCellHighlighted called for " + color + "\n");
    return new Point(-1, -1);
  }

  @Override
  public Point setCellHighlighted(Point point, PlayerColor color) {
    log.append("setCellHighlighted called with " + point + " for " + color + "\n");
    return point;
  }

  /**
   * Returns an immutable copy of the IPawnsBoard.
   *
   * @return a copy of the board
   */
  public IPawnsBoard copy() {
    log.append("copy called\n");
    MockPawnsBoard copy = new MockPawnsBoard(log, width, height);
    copy.scores = new ArrayList<>(this.scores);
    return copy;
  }

  @Override
  public Player getPlayer(PlayerColor color) {
    return null;
  }

  @Override
  public void addTurnListener(ModelActions listener) {
    // Stub for Mock.
  }

  @Override
  public void addGameOverListener(ModelActions listener) {
    // Stub for Mock.
  }

  @Override
  public void startGame() {
    // Stub for Mock.
  }

  @Override
  public void setDeckContents(String deckContents, PlayerColor color) {
    // Stub for Mock.
  }
}