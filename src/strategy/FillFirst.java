package strategy;

import model.Player;
import model.ReadonlyPawnsBoard;

/**
 * Fill first: Choose the first card and location that can be played on and play there.
 */
public class FillFirst implements PawnsBoardStrategy {
  @Override
  public Move chooseMove(ReadonlyPawnsBoard model, Player player) {
    if (model.getTurn() != player.getPlayerColor()) {
      throw new IllegalArgumentException("Not the right player's turn.");
    }
    for (int handIndex = 0; handIndex < player.getHand().size(); handIndex++) {
      for (int r = 0; r < model.getHeight(); r++) {
        for (int c = 0; c < model.getWidth(); c++) {
          if (model.isValid(player.getPlayerColor(), handIndex, r, c)) {
            return new Move(handIndex, r, c);
          }
        }
      }
    }
    return null;
  }
}
