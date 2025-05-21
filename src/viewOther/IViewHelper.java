package viewOther;

import viewOther.model.ICard;
import viewOther.model.PlayerColor;

import java.awt.Graphics;


/**
 * Interface for helper methods used to render visual components
 * such as cards and board cells in the Pawns Board game GUI.
 */
public interface IViewHelper {

  /**
   * Renders a card at a given position and size.
   *
   * @param g      Graphics context
   * @param card   the card to draw
   * @param color  the player color (used for fill color)
   * @param x      top-left x position
   * @param y      top-left y position
   * @param width  width of the card
   * @param height height of the card
   */
  void renderCard(Graphics g, ICard card, PlayerColor color,
                  int x, int y, int width, int height);

  /**
   * Draws a cell on the board, including background, border,
   * highlighting if selected, and either a card or pawn count if present.
   */
  void drawFullCell(Graphics g,
                    int row, int col, int x, int y, int w,
                    int h, int selectedRow, int selectedCol);


}

