package strategy;

import model.Player;
import model.PlayerColor;
import model.ReadonlyPawnsBoard;

/**
 * This class has the strategy of choosing the move that leaves their opponent in a situation with
 * no good moves. Not completed.
 */
public class Blocking implements PawnsBoardStrategy {

  /**
   * NOT FINISHED.
   * @param model the game model.
   * @param player the player that will use the strategies.
   * @return a strategy.
   */
  @Override
  public Move chooseMove(ReadonlyPawnsBoard model, Player player) {
    PawnsBoardStrategy mode = new ControlBoard();
    if (player.getPlayerColor() == PlayerColor.RED) {
      // Stub.
    }

    Move playersBestMove = mode.chooseMove(model, player);
    return null;
  }
}
