package view;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;
import java.awt.Point;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Font;

import controller.PlayerActionListener;
import model.Card;
import javax.swing.JPanel;

import model.PlayerColor;
import model.ReadonlyPawnsBoard;

/**
 * Represents an abstract pawns board panel that is a JPanel. This is the default state
 * of any panel used in a pawns board frame, and contains values and methods used by specific
 * component panels. 
 */
public abstract class AbstractPawnsBoardPanel extends JPanel {
  protected final ReadonlyPawnsBoard model;

  protected PlayerColor color;

  protected Color playerColor = new Color(255, 86, 86);
  protected final Color REDPLAYER = new Color(255, 86, 86);
  protected final Color BLUEPLAYER = new Color(118, 157, 255);
  protected final Color BOARDTILES = new Color(55, 55, 55);
  protected final Color SCORETILES = new Color(143, 137, 137);
  protected final Color INFLUENCEACTIVE = new Color(0, 208, 255);
  protected final Color INFLUENCEINACTIVE = new Color(46, 46, 46);
  protected final Color INFLUENCEORIGN = new Color(255, 212, 13);

  /**
   * Inits the abstractpawns board fields, given model and color of view.
   * @param model to use for model operations.
   * @param color to represent player as.
   */
  public AbstractPawnsBoardPanel(ReadonlyPawnsBoard model, PlayerColor color) {
    super();
    this.color = color;
    if (color == PlayerColor.BLUE) {
      this.playerColor = BLUEPLAYER;
    }

    this.model = model;
  }

  /**
   * Adds a click listener to specified components in this panel.
   * @param listener view action to add, that handles events.
   */
  public void addClickListener(PlayerActionListener listener) {
    this.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        // DO nothing as this is the default method overridden by extending methods.
      }
    });
  }

  /**
   * Convert pixel cordinates to model cordinates.
   * @param x pixel cordinate to convert.
   * @param y pixel cordinate to convert.
   * @return a point with the row, and col (row = x, col = y). Zero index based.
   */
  protected Point pixelToModel(int x, int y) {
    int cellWidthSize = Math.round(this.getWidth() / this.model.getWidth());
    int cellHeightSize = Math.round(this.getHeight() / this.model.getHeight());
    int row = (int) Math.floor(x / cellWidthSize);
    int col = (int) Math.floor(y / cellHeightSize);
    return new Point(row, col);
  }

  /**
   * Converts model cords to pixel cords.
   * @param row to convert to pixel.
   * @param col to ccnvert to pixel.
   * @return a point with x = to column cords in pixels, and y = row cords in pixels.
   */
  protected Point modelToPixel(int row, int col) {
    int cellWidthSize = Math.round(this.getWidth() / this.model.getWidth());
    int cellHeightSize = Math.round(this.getHeight() / this.model.getHeight());
    return new Point(col * cellWidthSize, row * cellHeightSize);
  }

  /**
   * Renders a card either on a board, or in the hand.
   * @param x pixel cord to render top left corner of card.
   * @param y pixel cord to render top left corner of card.
   * @param card card to render.
   * @param width width of card to render on.
   * @param height height of card to render on.
   * @param g2d graphics element.
   * @param onBoard is the card on the board? otherwise on hand.
   * @param rowLocal model cord for rows, 0 if in hand.
   * @param colLocal model cord for col, represents hand index.
   */
  protected void renderCard(int x, int y, Card card, int width, int height, Graphics2D g2d,
                  boolean onBoard, int rowLocal, int colLocal) {
    setColorForCard(card, g2d, onBoard, rowLocal, colLocal);
    g2d.fillRect(x, y, width, height);
    g2d.setColor(Color.BLACK);
    g2d.drawRect(x, y, width, height);

    if (!onBoard) {
      g2d.drawString(card.getName(), x + 10, y + 10);
      g2d.drawString("Cost: " + card.getCost(), x + 10, y + 20);
      g2d.drawString("Value: " + card.getValue(), x + 10, y + 30);
      int gridViewStartingX = x + 10;
      int gridViewStartingY = 40;
      int gridViewWidth;
      if (width < height) {
        gridViewWidth = width - 40;
      } else {
        gridViewWidth = height - 40;
      }

      int boxCellWidth = Math.round(gridViewWidth / 5);
      for (int row = 0; row < 5; row++) {
        for (int col = 0; col < 5; col++) {
          if (card.getInfluence().contains(Map.entry(row - 2, col - 2))) {
            g2d.setColor(INFLUENCEACTIVE);
          } else if (row - 2 == 0 && col - 2 == 0) {
            g2d.setColor(INFLUENCEORIGN);
          } else {
            g2d.setColor(INFLUENCEINACTIVE);
          }
          g2d.fillRect(gridViewStartingX + (boxCellWidth * col),
                  gridViewStartingY + (boxCellWidth * row), boxCellWidth, boxCellWidth);
        }
      }
    }
    else {
      g2d.setFont(new Font("Arial", Font.PLAIN, 40));
      g2d.drawString(Integer.toString(card.getValue()), x + width / 3, y + height / 2);
      g2d.setFont(new Font("Arial", Font.PLAIN, 10));
    }
  }

  /**
   * Sets the color of the card that is about to be palced.
   * @param card card to set color for.
   * @param g2d graphics to set color on.
   * @param onBoard is the card on the board?
   * @param rowLocal model cords of card, 0 if in hand.
   * @param colLocal model col cords of card, hand index if in hand.
   */
  private void setColorForCard(Card card, Graphics2D g2d, boolean onBoard,
                               int rowLocal, int colLocal) {
    if (card.getColor() == PlayerColor.RED) {
      g2d.setColor(REDPLAYER);
    }
    else {
      g2d.setColor(BLUEPLAYER);
    }
    if (onBoard) {
      if (new Point(colLocal, rowLocal).equals(this.model.getCellHighlighted(this.color))) {
        g2d.setColor(INFLUENCEACTIVE);
      }
    }
    else {
      if (colLocal == this.model.getHandHighlighted(this.color)) {
        g2d.setColor(INFLUENCEACTIVE);
      }
    }
  }

}
