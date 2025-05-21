package viewOther.adabters;

import java.util.ArrayList;
import java.util.List;

import model.Card;
import model.Player;
import viewOther.model.ICard;
import viewOther.model.IPlayer;
import viewOther.model.PlayerColor;

/**
 * Adapter to adapt from Iplayer to player. Takes a player adaptee.
 */
public class IPlayertoPlayer implements IPlayer {
  Player player;

  /**
   * Adapts a player used within the model.
   * @param player to take in to adapt.
   */
  public IPlayertoPlayer(Player player) {
    this.player = player;
  }

  @Override
  public PlayerColor getColor() {
    model.PlayerColor color = player.getPlayerColor();
    if (color == model.PlayerColor.RED) {
      return PlayerColor.RED;
    }
    else {
      return PlayerColor.BLUE;
    }
  }

  @Override
  public List<ICard> getHand() {
    List<ICard> hand = new ArrayList<ICard>();

    List<Card> actualHand = player.getHand();
    for (Card card : actualHand) {
      hand.add(new CardtoICard(card));
    }
    return hand;
  }
}
