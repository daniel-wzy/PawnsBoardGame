package viewOther;

import java.awt.Graphics;

/**
 * Represents the hand panel in the Pawns Board GUI.
 * Responsible for rendering the player's cards and highlighting a selected card.
 */
public interface IHandPanel {

  /**
   * Draws or refreshes the hand panel.
   *
   * @param g the Graphics context.
   */
  void drawHand(Graphics g);

  /**
   * Highlights the card at the specified index.
   *
   * @param cardIndex the index of the card to highlight.
   */
  void highlightCard(int cardIndex);

  /**
   * Clears any card highlight.
   */
  void clearCardHighlight();
}
