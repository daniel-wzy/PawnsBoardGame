package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import controller.ModelActions;

/**
 * Represents a basic pawns board game. Used to run an ensure game is being played correctly.
 * All row and columns are zero index based.
 */
public class BasicPawnsBoard implements IPawnsBoard {
  private boolean gameActive;
  private PlayerColor currentTurn;
  private Player playerRed;
  private Player playerBlue;
  private Board gameBoard;
  private int concurrentPasses;
  private final int handSize;

  private int handHighlighted = -1;
  private Point cellHighlighted;

  private int handBlueHighlighted = -1;
  private Point cellBlueHighlighted;

  private String redDeckFile;
  private String blueDeckFile;

  private List<ModelActions> turnListeners;
  private List<ModelActions> gameOverListeners;

  private final Random seed;
  private final boolean shuffle;
  /*
   * INVARIANT: Handsize is > 0.
   * Why: perserved by the constructor.
   * - not modified because it is final.
   * - will always evaluate to true.
   */

  /**
   * Represents a basicpawnsboard game with a specified deck file,
   * random seed, hand size, width, and height.
   * All indexes are zero based.
   *
   * @param redDeckFile string representing the file path of the desired deckfile for player red.
   * @param blueDeckFile string representing the file path of the desired deckfile for player blue.
   * @param shuffle          a boolean that determines if the deck is shuffled for players.
   * @param handSize         the size of a hand for players.
   * @param seed             a seed to use for players,
   * @param width            the width of the board.
   * @param height           the height of the board.
   * @throws IllegalArgumentException if deck file is null,
   *                                  or hand and or dimensions are less then or equal to zero.
   */
  public BasicPawnsBoard(String redDeckFile, String blueDeckFile,
                         boolean shuffle, int handSize, Random seed,
                         int width, int height) {
    if (redDeckFile == null || blueDeckFile == null || handSize <= 0 || width <= 0 || height <= 0) {
      throw new IllegalArgumentException("Arguments provided are not valid!");
    }
    this.currentTurn = PlayerColor.RED;
    this.playerRed = new BasicPlayer(PlayerColor.RED, handSize, seed, shuffle, "");
    this.playerBlue = new BasicPlayer(PlayerColor.BLUE, handSize, seed, shuffle, "");
    this.gameBoard = new BasicBoard(width, height);
    this.concurrentPasses = 0;
    this.handSize = handSize;
    this.turnListeners = new ArrayList<>();
    this.gameOverListeners = new ArrayList<>();
    this.gameActive = false;
    this.seed = seed;
    this.shuffle = shuffle;
  }

  @Override
  public void startGame() {
    this.gameActive = true;
    postToTurnListeners();
  }

  @Override
  public PlayerColor getTurn() {
    return this.currentTurn;
  }

  @Override
  public List<Card> getRedHand() {
    return playerRed.getHand();
  }

  @Override
  public List<Card> getBlueHand() {
    return playerBlue.getHand();
  }

  @Override
  public boolean isGameOver() {
    return concurrentPasses >= 2;
  }

  @Override
  public boolean isValid(PlayerColor color, int handIndex, int row, int col) {
    if (!this.gameActive) {
      return false;
    }
    if (handIndex < 0 || handIndex >= this.handSize) {
      return false;
    }
    Card card;
    if (color == PlayerColor.RED) {
      card = this.playerRed.getHand().get(handIndex);
    } else {
      card = this.playerBlue.getHand().get(handIndex);
    }
    return this.gameBoard.isValidPosition(row, col, color, (Cell) card);
  }

  @Override
  public void makeMove(PlayerColor color, int handIndex, int row, int col) {
    if (!this.gameActive) {
      return;
    }
    if (handIndex < 0 || handIndex >= handSize || row < 0
            || col < 0 || col >= this.gameBoard.getWidth()
            || row >= this.gameBoard.getHeight()) {
      throw new IllegalArgumentException("Arguments provided are not valid!");
    }
    Player player;
    if (color == PlayerColor.RED) {
      player = playerRed;
    } else {
      player = playerBlue;
    }
    if (currentTurn.equals(color) && handIndex < this.handSize) {
      try {
        this.gameBoard.placeCard(row, col, player.getHand().get(handIndex),
                color);
        player.takeCard(handIndex);
        this.nextTurn();
        this.concurrentPasses = 0;
      } catch (Exception e) {
        throw new IllegalStateException("Card can not be placed!");
      }
    }
  }


  @Override
  public void pass(PlayerColor color) {
    if (!this.gameActive) {
      return;
    }
    if (color == currentTurn) {
      this.concurrentPasses += 1;
      this.nextTurn();
    }
    if (this.concurrentPasses >= 2) {
      this.gameActive = false;
      postGameOverListeners();
    }
  }

  /**
   * Progresses the current turn to the next player.
   */
  private void nextTurn() {
    if (this.currentTurn == PlayerColor.RED) {
      this.currentTurn = PlayerColor.BLUE;
    } else {
      this.currentTurn = PlayerColor.RED;
    }
    postToTurnListeners();
  }

  @Override
  public List<Map.Entry<Integer, Integer>> getScores() {
    return this.gameBoard.getScores();
  }

  @Override
  public Map.Entry<Integer, Integer> getScore() {
    List<Map.Entry<Integer, Integer>> scores = this.getScores();
    int redScore = 0;
    int blueScore = 0;
    for (Map.Entry<Integer, Integer> entry : scores) {
      if (entry.getKey() > entry.getValue()) {
        redScore += entry.getKey();
      } else if (entry.getKey() < entry.getValue()) {
        blueScore += entry.getValue();
      }
    }
    Map<Integer, Integer> scoresMap = new HashMap<Integer, Integer>();
    scoresMap.put(redScore, blueScore);
    return scoresMap.entrySet().iterator().next();
  }

  @Override
  public List<List<Cell>> getBoard() {
    return this.gameBoard.getBoard();
  }

  @Override
  public int getWidth() {
    return this.gameBoard.getWidth();
  }

  @Override
  public int getHeight() {
    return this.gameBoard.getHeight();
  }

  @Override
  public Cell getCell(int x, int y) {
    return this.gameBoard.getCell(x, y).copy();
  }

  @Override
  public int getHandHighlighted(PlayerColor color) {
    if (color == PlayerColor.RED) {
      return this.handHighlighted;
    } else {
      return this.handBlueHighlighted;
    }
  }

  @Override
  public int setHandHighlighted(int handHighlighted, PlayerColor color) {
    if (color == PlayerColor.RED) {
      if (handHighlighted == this.handHighlighted) {
        return this.handHighlighted = -1;
      }
      return this.handHighlighted = handHighlighted;
    } else {
      if (handHighlighted == this.handBlueHighlighted) {
        return this.handBlueHighlighted = -1;
      }
      return this.handBlueHighlighted = handHighlighted;
    }
  }

  @Override
  public Point getCellHighlighted(PlayerColor color) {
    Point cellHighlighted;
    if (color == PlayerColor.RED) {
      cellHighlighted = this.cellHighlighted;
    } else {
      cellHighlighted = this.cellBlueHighlighted;
    }
    if (cellHighlighted == null) {
      return new Point(-1, -1);
    }
    return new Point(cellHighlighted.x, cellHighlighted.y);
  }

  @Override
  public Point setCellHighlighted(Point point, PlayerColor color) {
    if (color == PlayerColor.RED) {
      if (point.equals(this.cellHighlighted)) {
        this.cellHighlighted = new Point(-1, -1);
      } else {
        this.cellHighlighted = point;
      }
      return this.cellHighlighted;
    } else {
      if (point.equals(this.cellBlueHighlighted)) {
        this.cellBlueHighlighted = new Point(-1, -1);
      } else {
        this.cellBlueHighlighted = point;
      }
      return this.cellBlueHighlighted;
    }
  }

  @Override
  public Player getPlayer(PlayerColor color) {
    if (color == PlayerColor.RED) {
      return this.playerRed.copy();
    }
    else {
      return this.playerBlue.copy();
    }
  }

  @Override
  public void addTurnListener(ModelActions listener) {
    this.turnListeners.add(listener);
  }

  @Override
  public void addGameOverListener(ModelActions listener) {
    this.gameOverListeners.add(listener);
  }

  /**
   * Posts a notification to all listeners that a turn has been changed.
   */
  private void postToTurnListeners() {
    for (ModelActions listener : this.turnListeners) {
      listener.handleNextTurn();
    }
  }

  /**
   * Posts a notification to all listeners that the game is over.
   */
  private void postGameOverListeners() {
    for (ModelActions listener : this.gameOverListeners) {
      listener.handleGameOver();
    }
  }

  @Override
  public IPawnsBoard copy() {
    Board boardCopy = this.gameBoard.copy();
    Player redPlayerCopy = this.playerRed.copy();
    Player bluePlayerCopy = this.playerBlue.copy();

    BasicPawnsBoard copy = new BasicPawnsBoard(this.redDeckFile,
            this.blueDeckFile, false, this.handSize, new Random(), boardCopy.getWidth(),
            boardCopy.getHeight());

    copy.currentTurn = this.currentTurn;
    copy.gameBoard = boardCopy;
    copy.playerRed = redPlayerCopy;
    copy.playerBlue = bluePlayerCopy;
    copy.concurrentPasses = this.concurrentPasses;
    copy.handHighlighted = this.handHighlighted;
    copy.cellHighlighted = (this.cellHighlighted == null) ? null :
            new Point(this.cellHighlighted);
    copy.handBlueHighlighted = this.handBlueHighlighted;
    copy.cellBlueHighlighted = (this.cellBlueHighlighted == null) ? null :
            new Point(this.cellBlueHighlighted);

    return copy;
  }

  @Override
  public void setDeckContents(String deckContents, PlayerColor color) {
    if (color == PlayerColor.RED) {
      this.redDeckFile = deckContents;
      this.playerRed = new BasicPlayer(PlayerColor.RED, handSize,
              this.seed, this.shuffle, redDeckFile);
    }
    else {
      this.blueDeckFile = deckContents;
      this.playerBlue = new BasicPlayer(PlayerColor.BLUE, handSize,
              this.seed, this.shuffle, redDeckFile);
    }
  }

}
