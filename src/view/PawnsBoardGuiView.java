package view;

import controller.PlayerActionListener;

/**
 * Represents a view representation of the pawns board game from a certain players perspective.
 */
public interface PawnsBoardGuiView {

  /**
   * Set up the controller to handle click events in this view.
   * @param listener the controller
   */
  void addClickListener(PlayerActionListener listener);

  /**
   * Refresh the view to reflect any changes in the game state.
   */
  void refresh();

  /**
   * Make the view visible to start the game session.
   */
  void makeVisible();

  /**
   * Displays a message notification to player.
   * @param msg to use in message field.
   */
  void displayMsg(String msg);
}
