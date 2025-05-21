package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a basic board. Used to initialize the board, set or remove values for cells
 * and apply card influence.
 */
public class BasicBoard implements Board {
  List<List<Cell>> board = new ArrayList<>();

  public BasicBoard(int width, int height) {
    initHelper(width, height);
  }

  /**
   * Helper to intialize the game board.
   *
   * @param width  to set board to.
   * @param height to set board to.
   */
  private void initHelper(int width, int height) {
    for (int row = 0; row < height; row++) {
      List<Cell> rowCells = new ArrayList<>();
      for (int col = 0; col < width; col++) {
        if (col == 0) {
          rowCells.add(new BasicPawn(PlayerColor.RED, 1));
        } else if (col == width - 1) {
          rowCells.add(new BasicPawn(PlayerColor.BLUE, 1));
        } else {
          rowCells.add(new BasicPawn());
        }
      }
      this.board.add(rowCells);
    }
  }

  /**
   * Sets the value of a specific cell on the board.
   *
   * @param row  The row index of the cell.
   * @param col  The column index of the cell.
   * @param card The card to set for the specified cell.
   * @throws IllegalArgumentException if the cell position is invalid.
   */
  private void setCard(int row, int col, Card card) {
    if (!this.inBounds(row, col)) {
      throw new IllegalArgumentException("Invalid row or col!");
    }
    this.board.get(row).set(col, (Cell) card);
  }

  @Override
  public Cell getCell(int row, int col) {
    return this.board.get(row).get(col).copy();
  }

  @Override
  public List<List<Cell>> getBoard() {
    List<List<Cell>> boardCopy = new ArrayList<>();
    for (int row = 0; row < this.board.size(); row++) {
      List<Cell> rowCells = new ArrayList<>();
      for (int col = 0; col < this.board.get(0).size(); col++) {
        rowCells.add(this.board.get(row).get(col).copy());
      }
      boardCopy.add(rowCells);
    }
    return boardCopy;
  }

  /**
   * Checks if a given position fits the given bounds of the board.
   *
   * @param row The row index.
   * @param col The column index.
   * @return true if the position is within the bounds and false if not.
   */
  public boolean isValidPosition(int row, int col, PlayerColor color, Cell target) {
    if (this.inBounds(row, col)) {
      Cell currentCell = this.board.get(row).get(col).copy();
      if (target.getPlayerColor() == color && target.getPawns() <= 3) {
        return currentCell.getPawns() > 0 && currentCell.getPlayerColor() == color;
      }
    }
    return false;
  }

  @Override
  public void placeCard(int row, int col, Card card, PlayerColor color) {
    Cell target = this.board.get(row).get(col).copy();
    if (this.isValidPosition(row, col, color, target)) {
      if (target.getPawns() >= card.getCost()) {
        this.setCard(row, col, card);
        influenceHelper(row, col, color, card.getInfluence());
      }
    } else {
      throw new IllegalArgumentException("Invalid row or col!");
    }
  }

  @Override
  public int getWidth() {
    return this.board.get(0).size();
  }

  @Override
  public int getHeight() {
    return this.board.size();
  }

  @Override
  public List<Map.Entry<Integer, Integer>> getScores() {
    List<Map.Entry<Integer, Integer>> scores = new ArrayList<>();
    for (List<Cell> row : this.board) {
      int redScore = 0;
      int blueScore = 0;
      Map<Integer, Integer> pair = new HashMap<>();
      for (Cell cell : row) {
        Map.Entry<Integer, Integer> score = cell.getScore();
        redScore += score.getKey();
        blueScore += score.getValue();
      }
      pair.put(redScore, blueScore);
      scores.add(pair.entrySet().iterator().next());
    }
    return scores;
  }

  /**
   * Checks if a given row col pair is in bounds for this board.
   *
   * @param row to check.
   * @param col to check.
   * @return a boolean if in bounds.
   */
  private boolean inBounds(int row, int col) {
    return row < this.board.size() && col < this.board.get(0).size()
            && row >= 0 && col >= 0;
  }

  /**
   * Influences the cells areound a placed card.
   *
   * @param row       of card position.
   * @param col       of card position.
   * @param color     to influence from.
   * @param influence influence pattern Map(Row, Col) RELATIVE to card position.
   */
  private void influenceHelper(int row,
                               int col,
                               PlayerColor color,
                               List<Map.Entry<Integer, Integer>> influence) {

    for (Map.Entry<Integer, Integer> entry : influence) {
      int targetRow = row + entry.getKey();
      int targetCol = col + entry.getValue();
      if (inBounds(targetRow, targetCol)) {
        this.board.get(targetRow).get(targetCol).influence(color);
      }
    }
  }

  /**
   * Returns an immutable copy of the game board.
   *
   * @return the model's board
   */
  @Override
  public BasicBoard copy() {
    BasicBoard boardCopy = new BasicBoard(this.getWidth(), this.getHeight());
    for (int r = 0; r < this.getHeight(); r++) {
      for (int c = 0; c < this.getWidth(); c++) {
        boardCopy.board.get(r).set(c, this.board.get(r).get(c).copy());
      }
    }
    return boardCopy;
  }
}
