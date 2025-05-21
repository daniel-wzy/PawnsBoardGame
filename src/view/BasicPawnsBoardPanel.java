package view;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.awt.Point;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Graphics;

import controller.PlayerActionListener;
import model.Card;
import model.Cell;
import model.PlayerColor;
import model.ReadonlyPawnsBoard;

/**
 * Represents the panel containing all the cells in a board. This panel
 * is in charge of visualizing the pawns and cards placed on a board.
 */
public class BasicPawnsBoardPanel extends AbstractPawnsBoardPanel {

  /**
   * Creates a new pawns board with a given model and color.
   * @param model to use to visualize board.
   * @param color to represent board as.
   */
  public BasicPawnsBoardPanel(ReadonlyPawnsBoard model, PlayerColor color) {
    super(model, color);
  }

  @Override
  public void addClickListener(PlayerActionListener listenerBoard) {
    this.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        Point modelCords = pixelToModel(e.getX(), e.getY());
        listenerBoard.handleCellClick(modelCords.x, modelCords.y, color);
      }
    });
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D)g.create();

    init(g2d);

    drawCells(g2d);
  }

  /**
   * Helper method to init the board, drawing the lines.
   * @param g2d to draw on.
   */
  private void init(Graphics2D g2d) {
    int cellWidthSize = Math.round(this.getWidth() / this.model.getWidth());
    int cellHeightSize = Math.round(this.getHeight() / this.model.getHeight());

    this.setBackground(BOARDTILES);
    g2d.setColor(Color.BLACK);
    for (int row = 1; row < this.model.getHeight(); row++) {
      g2d.drawLine(0, row * cellHeightSize, this.getWidth(), row * cellHeightSize);
    }
    for (int col = 1; col < this.model.getWidth(); col++) {
      g2d.drawLine(col * cellWidthSize, 0, col * cellWidthSize, this.getHeight());
    }
    for (int row = 0; row < this.model.getHeight(); row++) {
      for (int col = 0; col < this.model.getWidth(); col++) {
        if (new Point(col, row).equals(this.model.getCellHighlighted(this.color))) {
          g2d.setColor(INFLUENCEACTIVE);
          g2d.fillRect(col * cellWidthSize, row * cellHeightSize, cellWidthSize, cellHeightSize);
        }
      }
    }
  }

  /**
   * Helper method to draw cells on to g2d, drawing pawns, or cards, depending on what
   * the cell represents.
   * @param g2d to draw cells on to.
   */
  private void drawCells(Graphics2D g2d) {
    List<List<Cell>> cellList = this.model.getBoard();
    int cellWidthSize = Math.round(this.getWidth() / this.model.getWidth());
    int cellHeightSize = Math.round(this.getHeight() / this.model.getHeight());
    g2d.setColor(BOARDTILES);
    for (int row = 0; row < this.model.getHeight(); row++) {
      for (int col = 0; col < this.model.getWidth(); col++) {
        Cell cell = cellList.get(row).get(col);
        if (cell.getPawns() > 0) {
          Point cellCords = modelToPixel(row, col);
          drawPawnCell(g2d, cell.getPawns(), cell.getPlayerColor(), cellCords.x, cellCords.y);
        }
        else if (cell.getScore().getValue() > 0 || cell.getScore().getKey() > 0) {
          Point cellCords = modelToPixel(row, col);
          renderCard(cellCords.x, cellCords.y, (Card) cell,
                  cellWidthSize, cellHeightSize, g2d, true, row, col);
        }
      }
    }
  }

  /**
   * Helper method to draw a pawn cell. Wil align pawns in a diagonal line equally spaced.
   * @param g2d to draw pawn cell on to.
   * @param pawns number of pawns to draw on cell.
   * @param color of pawns to draw.
   * @param x coordinate of the cell to draw pawns in.
   * @param y coordinate of the cell to draw pawns in.
   */
  private void drawPawnCell(Graphics2D g2d, int pawns, PlayerColor color, int x, int y) {
    int offsetX = Math.round(this.getWidth() / (this.model.getWidth() * (pawns + 1)));
    int offsetY = Math.round(this.getHeight() / (this.model.getHeight() * (pawns + 1)));

    for (int i = 0; i < pawns; i++) {
      if (color == PlayerColor.RED) {
        g2d.setColor(REDPLAYER);
      }
      else {
        g2d.setColor(BLUEPLAYER);
      }
      g2d.fillOval((offsetX + x - 20) + i * offsetX, (offsetY + y - 20) + i * offsetY, 40, 40);
      g2d.setColor(Color.black);
    }
  }

}
