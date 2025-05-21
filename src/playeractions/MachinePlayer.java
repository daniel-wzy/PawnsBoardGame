package playeractions;

import controller.PlayerActionListener;
import model.Player;
import model.PlayerColor;
import model.ReadonlyPawnsBoard;
import strategy.Move;
import strategy.PawnsBoardStrategy;

/**
 * This class is for machine players.
 */
public class MachinePlayer implements PlayerActions {
  private final PlayerColor player;
  private final PawnsBoardStrategy strategy;
  private PlayerActionListener listener;

  /**
   * Constructs an action for machine players.
   *
   * @param player the machine player
   * @param strategy the strategy that the machine chooses
   */
  public MachinePlayer(PlayerColor player, PawnsBoardStrategy strategy) {
    this.player = player;
    this.strategy = strategy;
  }

  @Override
  public void takeTurn(ReadonlyPawnsBoard model) {
    Player gamePlayer = model.copy().getPlayer(player);
    Move move = strategy.chooseMove(model, gamePlayer);
    if (move != null) {
      if (listener != null) {
        this.listener.handleCellClick(move.c, move.r, this.player);
        if (model.getHandHighlighted(this.player) != move.handindex) {
          this.listener.handleHandClick(move.handindex, this.player);
        }
        this.listener.confirmMove(this.player);
      }
    } else {
      if (listener != null) {
        listener.confirmPass(this.player);
      }
    }
  }

  @Override
  public void addListener(PlayerActionListener listener) {
    this.listener = listener;
  }

  @Override
  public PlayerColor getPlayerColor() {
    return this.player;
  }
}
