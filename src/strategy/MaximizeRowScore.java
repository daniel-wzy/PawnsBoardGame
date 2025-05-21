package strategy;

import java.util.Map;
import model.IPawnsBoard;
import model.Player;
import model.Card;
import model.PlayerColor;
import model.ReadonlyPawnsBoard;

/**
 * This strategy will maximize the row score.
 */
public class MaximizeRowScore implements PawnsBoardStrategy {
  @Override
  public Move chooseMove(ReadonlyPawnsBoard model, Player player) {
    if (model.getTurn() != player.getPlayerColor()) {
      throw new IllegalArgumentException("Not your turn.");
    }

    for (int r = 0; r < model.getHeight(); r++) {
      int currentScore;
      int opponentScore;
      Map.Entry<Integer, Integer> rowScore = model.getScores().get(r);
      if (player.getPlayerColor() == PlayerColor.RED) {
        currentScore = rowScore.getKey();
        opponentScore = rowScore.getValue();
      } else {
        currentScore = rowScore.getValue();
        opponentScore = rowScore.getKey();
      }

      if (currentScore <= opponentScore) {
        for (int handIndex = 0; handIndex < player.getHand().size(); handIndex++) {
          Card card = player.getHand().get(handIndex);
          for (int c = 0; c < model.getWidth(); c++) {
            if (model.getCell(r, c).getPawns() < card.getCost()) {
              continue;
            }
            if (model.isValid(player.getPlayerColor(), handIndex, r, c)) {
              // Creates a readonly model
              IPawnsBoard modelCopy = model.copy();
              modelCopy.makeMove(player.getPlayerColor(), handIndex, r, c);
              int newScore;
              int newOpponentScore;
              Map.Entry<Integer, Integer> newRowScore = modelCopy.getScores().get(r);
              if (player.getPlayerColor() == PlayerColor.RED) {
                newScore = newRowScore.getKey();
                newOpponentScore = newRowScore.getValue();
              } else {
                newScore = newRowScore.getValue();
                newOpponentScore = newRowScore.getKey();
              }
              if (newScore > newOpponentScore) {
                return new Move(handIndex, r, c);
              }
            }
          }
        }
      }
    }
    return null;
  }
}