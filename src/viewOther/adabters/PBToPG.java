package viewOther.adabters;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import model.Card;
import model.Cell;
import model.ReadonlyPawnsBoard;
import viewOther.model.ICard;
import viewOther.model.ICell;
import viewOther.model.IPlayer;
import viewOther.model.PlayerColor;
import viewOther.model.ReadonlyPawnsGame;

/**
 * An adapter to adapt a pawns board model to a pawns game model.
 */
public class PBToPG implements ReadonlyPawnsGame {
  ReadonlyPawnsBoard adaptee;
  model.PlayerColor perspective;

  /**
   * Adapts adaptee pawnsboard to game model.
   * @param adaptee model to adapt.
   * @param perspective to adapt to.
   */
  public PBToPG(ReadonlyPawnsBoard adaptee, model.PlayerColor perspective) {
    this.adaptee = adaptee;
    this.perspective = perspective;
  }

  @Override
  public ICell[][] getBoardState() {
    ICell[][] board = new ICell[adaptee.getHeight()][adaptee.getWidth()];
    List<List<Cell>> modelBoard = adaptee.getBoard();
    for (int row = 0; row < adaptee.getHeight(); row++) {
      for (int col = 0; col < adaptee.getWidth(); col++) {
        board[row][col] = new CELLtoICELL(modelBoard.get(row).get(col), adaptee);
      }
    }
    return board;
  }

  @Override
  public int[] getRowScore(int row) {
    List<Map.Entry<Integer, Integer>> scores = adaptee.getScores();
    Map.Entry<Integer, Integer> entry = scores.get(row);

    int[] rowScore = new int[2];
    rowScore[0] = entry.getKey();
    rowScore[1] = entry.getValue();
    return rowScore;
  }

  @Override
  public IPlayer getWinner() {
    return null;
  }


  @Override
  public boolean isGameOver() {
    return adaptee.isGameOver();
  }

  // Other model.

  @Override
  public IPlayer getCurrentPlayer() {
    model.PlayerColor currentPlayerColor = this.adaptee.getTurn();
    return new IPlayertoPlayer(this.adaptee.getPlayer(currentPlayerColor));
  }

  @Override
  public boolean isValidMove(int cardIdx, int row, int col) {
    return this.adaptee.isValid(perspective, cardIdx, row, col);
  }

  @Override
  public int getNumRows() {
    return this.adaptee.getHeight();
  }

  @Override
  public int getNumCols() {
    return this.adaptee.getWidth();
  }

  @Override
  public ICell getCellAt(int row, int col) {
    return new CELLtoICELL(this.adaptee.getCell(row, col), adaptee);
  }

  @Override
  public List<ICard> getPlayerHand(PlayerColor color) {
    List<ICard> hand = new ArrayList<>();
    List<Card> oldHand = new ArrayList<>();
    if (color == PlayerColor.RED) {
      oldHand = this.adaptee.getRedHand();
    }
    else {
      oldHand = this.adaptee.getBlueHand();
    }
    for (Card card : oldHand) {
      hand.add(new CardtoICard(card));
    }
    return hand;
  }

  @Override
  public IPlayer getCellOwner(int row, int col) {
    model.PlayerColor ownerColor = this.adaptee.getCell(row, col).getPlayerColor();
    IPlayer owner = new IPlayertoPlayer(this.adaptee.getPlayer(ownerColor));
    return owner;
  }

  @Override
  public int getPlayerScore(PlayerColor color) {
    Map.Entry<Integer, Integer> score = this.adaptee.getScore();
    if (color == PlayerColor.RED) {
      return score.getKey();
    }
    else {
      return score.getValue();
    }
  }

  @Override
  public int[][] getCardInfluenceGrid(PlayerColor color, ICard card) {
    return card.getInfluenceGrid();
  }
}
