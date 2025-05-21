package playeractions;

import controller.PlayerActionListener;
import model.PlayerColor;
import model.ReadonlyPawnsBoard;

/**
 * This class is for human players.
 */
public class HumanPlayer implements PlayerActions {
  private PlayerActionListener listener;
  private PlayerColor color;

  public HumanPlayer(PlayerColor color) {
    this.color = color;
  }

  @Override
  public void takeTurn(ReadonlyPawnsBoard model) {
    listener.displayMessage(this.color, "Its your turn!");
  }

  @Override
  public void addListener(PlayerActionListener listener) {
    this.listener = listener;
  }

  @Override
  public PlayerColor getPlayerColor() {
    return this.color;
  }
}