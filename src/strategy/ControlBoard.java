package strategy;

import model.Card;
import model.Cell;
import model.IPawnsBoard;
import model.Player;
import model.PlayerColor;
import model.ReadonlyPawnsBoard;

import java.awt.Point;
import java.util.List;

/**
 * Control the board: choose a card and location that will give the current player ownership of the
 * most cells. In a tie between positions, choose the uppermost-leftmost (so uppermost first, then
 * leftmost). In a tie between cards, choose the leftmost (or closest to first) card.
 */
public class ControlBoard implements PawnsBoardStrategy {
  @Override
  public Move chooseMove(ReadonlyPawnsBoard model, Player player) {
    List<Card> hand;
    if (player.getPlayerColor() == PlayerColor.RED) {
      hand = model.getRedHand();
    }
    else {
      hand = model.getBlueHand();
    }
    Move bestMove = null;
    int bestPawns = 0;
    for (int handIndex = 0; handIndex < hand.size(); handIndex++) {
      IPawnsBoard modelCopy = model.copy();
      Move newMove = bruteForceHelper(player.getPlayerColor(), handIndex, modelCopy);
      if (newMove != null) {
        modelCopy.makeMove(player.getPlayerColor(), handIndex, newMove.r, newMove.c);
        int newMovePawns = getPawnsOwned(modelCopy, player.getPlayerColor());
        if (newMovePawns > bestPawns) {
          bestMove = new Move(newMove.handindex, newMove.r, newMove.c);
          bestPawns = newMovePawns;
        }
      }
    }
    return bestMove;
  }

  private Move bruteForceHelper(PlayerColor player, int handIndex, IPawnsBoard model) {
    int highValue = 0;
    Point highPoint = null;
    for (int row = 0; row < model.getHeight(); row++) {
      for (int col = 0; col < model.getWidth(); col++) {
        if (model.isValid(player, handIndex, row, col)) {
          IPawnsBoard modelCopy = model.copy();
          modelCopy.makeMove(player, handIndex, row, col);
          if (getPawnsOwned(modelCopy, player) > highValue) {
            highPoint = new Point(row, col);
            highValue = getPawnsOwned(modelCopy, player);
          }
        }
      }
    }
    if (highPoint != null) {
      return new Move(handIndex, highPoint.x, highPoint.y);
    }
    else {
      return null;
    }
  }

  private int getPawnsOwned(IPawnsBoard board, PlayerColor color) {
    int pawnsOwned = 0;
    for (List<Cell> cards : board.getBoard()) {
      for (Cell card : cards) {
        if (card.getPlayerColor() == color) {
          pawnsOwned += card.getPawns();
        }
      }
    }
    return pawnsOwned;
  }
}
