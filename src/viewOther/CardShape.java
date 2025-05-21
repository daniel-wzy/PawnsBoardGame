package viewOther;

import java.awt.geom.Path2D;
import java.awt.geom.RoundRectangle2D;

/**
 * A reusable rounded rectangle shape for a card.
 */
public class CardShape extends Path2D.Double {

  /**
   * Constructs a rounded rectangle shape for a card.
   *
   * @param x      the x coordinate of the card's top-left corner
   * @param y      the y coordinate of the card's top-left corner
   * @param width  the width of the card
   * @param height the height of the card
   */
  public CardShape(int x, int y, int width, int height) {
    super();
    RoundRectangle2D.Double shape = new RoundRectangle2D.Double(x, y, width, height, 15, 15);
    append(shape, false);
  }
}
