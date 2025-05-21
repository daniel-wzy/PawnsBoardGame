package viewOther;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;

import viewOther.model.ICard;
import viewOther.model.PlayerColor;
import viewOther.model.ReadonlyPawnsGame;

/**
 * Helper class that provides rendering utilities for drawing cards and cells
 * in the GUI view of PawnsBoard.
 */
public class ViewHelper implements IViewHelper {

  private final ReadonlyPawnsGame model;

  /**
   * Constructs a new {@code ViewHelper} using the given read-only game model.
   *
   * @param model the game model to read from
   */
  public ViewHelper(ReadonlyPawnsGame model) {
    this.model = model;
  }

  /**
   * Renders a card at a specified position with the given dimensions and player color.
   */
  public void renderCard(Graphics g, ICard card, PlayerColor color,
                         int x, int y, int width, int height) {
    Graphics2D g2d = (Graphics2D) g.create();

    // Card background
    CardShape shape = new CardShape(x, y, width, height);
    g2d.setColor(color == PlayerColor.RED ? Color.RED : Color.BLUE);
    g2d.fill(shape);
    g2d.setColor(Color.BLACK);
    g2d.draw(shape);

    String cardText = card.toString();
    String gridText = gridToText(model.getCardInfluenceGrid(color, card));
    String fullText = cardText + "\n" + gridText;
    String[] lines = fullText.split("\n");

    int maxFontSize = height / lines.length;
    Font font = null;
    FontMetrics fm = null;

    for (int size = maxFontSize; size >= 5; size--) {
      font = new Font("Monospaced", Font.PLAIN, size);
      fm = g2d.getFontMetrics(font);
      int totalHeight = fm.getHeight() * lines.length;
      if (totalHeight <= height - 10) {
        break;
      }
    }

    g2d.setFont(font);
    g2d.setColor(Color.WHITE);
    int startY = y + fm.getAscent() + 5;
    drawMultilineString(g2d, fullText, x + 10, startY);

    g2d.dispose();
  }

  /**
   * Draws a cell on the board, including background, border,
   * highlighting if selected, and either a card or pawn count if present.
   */

  public void drawFullCell(Graphics g, int row, int col, int x, int y, int w, int h,
                           int selectedRow, int selectedCol) {
    Color cellColor = Color.WHITE;
    if (model.getCellOwner(row, col) != null) {
      PlayerColor color = model.getCellOwner(row, col).getColor();
      cellColor = (color == PlayerColor.RED) ? new Color(255, 200, 200)
              : new Color(180, 200, 255);
    }

    g.setColor(cellColor);
    g.fillRect(x, y, w, h);

    if (row == selectedRow && col == selectedCol) {
      g.setColor(Color.CYAN);
      g.fillRect(x, y, w, h);
    }

    g.setColor(Color.BLACK);
    g.drawRect(x, y, w, h);

    if (model.getCellAt(row, col).hasCard()) {
      ICard card = model.getCellAt(row, col).getCard();
      PlayerColor ownerColor = model.getCellOwner(row, col).getColor();
      renderCard(g, card, ownerColor, x, y, w, h);
    } else {
      drawPawnInfo(g, row, col, x, y);
    }
  }

  private void drawPawnInfo(Graphics g, int row, int col, int x, int y) {
    int pawnCount = model.getCellAt(row, col).getPawnCount();
    String text = " " + pawnCount;
    drawMultilineString(g, text, x + 5, y + 20);
  }

  /**
   * Draws text on the screen by splitting on newline characters.
   *
   * @param g    the Graphics object.
   * @param text the text to draw.
   * @param x    the x-coordinate for the text
   * @param y    the starting y-coordinate for the first line
   */
  private void drawMultilineString(Graphics g, String text, int x, int y) {
    FontMetrics m = g.getFontMetrics();
    for (String line : text.split("\n")) {
      g.drawString(line, x, y);
      y += m.getHeight();
    }
  }

  /**
   * Converts a 5x5 influence grid into a text representation.
   */
  private String gridToText(int[][] grid) {
    StringBuilder sb = new StringBuilder();
    for (int r = 0; r < 5; r++) {
      for (int c = 0; c < 5; c++) {
        int val = grid[r][c];
        if (val == 1) {
          sb.append("I");
        } else if (val == 2) {
          sb.append("C");
        } else {
          sb.append("X");
        }
      }
      sb.append("\n");
    }
    return sb.toString();
  }
}