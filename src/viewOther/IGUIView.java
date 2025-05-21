package viewOther;

import java.awt.Component;

import viewOther.controller.Features;


/**
 * Represents the main GUI view for the Pawns Board game.
 * Provides methods for displaying the view and attaching event listeners.
 */
public interface IGUIView {

  /**
   * Makes the view visible to the user.
   */
  void makeVisible();

  /**
   * Gets Width of hand Panel.
   */
  int getHandWidth();

  /**
   * Highlights the selected card by its index in hand.
   *
   * @param cardIndex index of the selected card.
   */
  void highlightCard(int cardIndex);

  /**
   * Highlights the selected cell on the board.
   *
   * @param row the row of the selected cell.
   * @param col the column of the selected cell.
   */
  void highlightCell(int row, int col);

  /**
   * Gets the boards height.
   *
   * @return board's height.
   */
  int getBoardHeight();

  /**
   * Gets the boards width.
   *
   * @return board's width.
   */
  int getBoardWidth();

  /**
   * Registers a listener that will be notified when the user performs an action in the view.
   *
   * @param listener the listener to register for features(player actiosn)
   * @throws IllegalArgumentException if the listener is null
   */
  void addFeatures(Features listener);

  /**
   * Returns the main component for use in dialogs.
   *
   * @return the  component of this view(JFrame)
   */
  Component getComponent();

  /**
   * Fixes focus so it can receive key events.
   */
  void resetFocus();

  /**
   * Sets the window title.
   *
   * @param title the new title
   */
  void setTitle(String title);

  /**
   * Repaints the board and hand panels to reflect any model changes.
   */
  void refresh();
}
