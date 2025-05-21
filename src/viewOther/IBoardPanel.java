package viewOther;

import java.awt.Graphics;

/**
 * Represents the board panel in the Pawns Board GUI.
 * Responsible for rendering the game grid, cells, pawns, and cards on the board.
 */
public interface IBoardPanel {

  /**
   * Draws the board.
   *
   * @param g the Graphics context.
   */
  void drawBoard(Graphics g);

  /**
   * Highlights a cell at the given row and column.
   *
   * @param row the row index of the cell.
   * @param col the column index of the cell.
   */
  void highlightCell(int row, int col);

  /**
   * Clears the cell highlight.
   */
  void clearCellHighlight();
}
