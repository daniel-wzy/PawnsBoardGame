package viewOther;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import javax.swing.JPanel;

import viewOther.model.ReadonlyPawnsGame;

/**
 * A JPanel that renders the game board based on the ReadonlyPawnsGame model.
 */
public class BoardPanel extends JPanel implements IBoardPanel {

  private final ReadonlyPawnsGame model;
  private final ViewHelper delegate;
  protected int selectedRow = -1;
  protected int selectedCol = -1;

  /**
   * Constructs a panel that displays the game board using the given read-only model.
   *
   * @param model the read-only game model
   * @throws IllegalArgumentException if the model is null
   */
  public BoardPanel(ReadonlyPawnsGame model) {
    this.model = model;
    this.delegate = new ViewHelper(model);
    this.setPreferredSize(new Dimension(600, 400));
    this.setBackground(Color.WHITE);
    clearCellHighlight();
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    drawBoard(g);
  }

  @Override
  public void drawBoard(Graphics g) {
    int rows = model.getNumRows();
    int cols = model.getNumCols();

    int cellWidth = getWidth() / (cols + 2);
    int cellHeight = getHeight() / rows;

    FontMetrics fm = g.getFontMetrics();
    for (int r = 0; r < rows; r++) {
      int[] rowScore = model.getRowScore(r);

      // RED score column (column 0)
      String redScoreText = String.valueOf(rowScore[0]);
      int redTextWidth = fm.stringWidth(redScoreText);
      int redX = (cellWidth - redTextWidth) / 2; // center within column 0
      int textY = r * cellHeight + 20;
      g.setColor(Color.BLACK);
      g.drawString(redScoreText, redX, textY);

      // Draw the board cells starting at column 1
      for (int c = 0; c < cols; c++) {
        int x = (c + 1) * cellWidth;
        int y = r * cellHeight;

        delegate.drawFullCell(g, r, c, x, y, cellWidth, cellHeight, selectedRow, selectedCol);
      }

      String blueScoreText = String.valueOf(rowScore[1]);
      int blueTextWidth = fm.stringWidth(blueScoreText);
      int blueX = (cols + 1) * cellWidth + (cellWidth - blueTextWidth) / 2;
      g.setColor(Color.BLACK);
      g.drawString(blueScoreText, blueX, textY);
    }
  }

  @Override
  public void highlightCell(int row, int col) {
    if (row == selectedRow && col == selectedCol) {
      clearCellHighlight(); // deselect
    } else {
      this.selectedRow = row;
      this.selectedCol = col;
    }
    repaint();
  }

  @Override
  public void clearCellHighlight() {
    this.selectedRow = -1;
    this.selectedCol = -1;
    repaint();
  }
}