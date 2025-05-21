package strategy;

import model.Player;
import model.ReadonlyPawnsBoard;

/**
 * Interface for all the strategies available for Pawns Board game.
 */
public interface PawnsBoardStrategy {
  /**
   * Returns a move or the player passes the turn.
   *
   * @param model the game model
   * @param player the player that will use the strategies
   * @return the move made
   */
  Move chooseMove(ReadonlyPawnsBoard model, Player player);
}
