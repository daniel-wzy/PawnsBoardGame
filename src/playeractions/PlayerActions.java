package playeractions;

import controller.PlayerActionListener;
import model.PlayerColor;
import model.ReadonlyPawnsBoard;

/**
 * Represents the source of an action for all game input.
 */
public interface PlayerActions {
  /**
   * Called when it is the player’s turn. The implementation should
   * decide what move to make and then publish it.
   * For machine players, this computes the move using a strategy.
   * @param model the ReadOnlyModel
   */
  void takeTurn(ReadonlyPawnsBoard model);

  /**
   * Adds a listener to be notified when the player acts.
   * @param listener the listener of the act
   */
  void addListener(PlayerActionListener listener);

  /**
   * Gets the color of this player.
   * @return a PlayerColor representing this player's color.
   */
  PlayerColor getPlayerColor();
}
