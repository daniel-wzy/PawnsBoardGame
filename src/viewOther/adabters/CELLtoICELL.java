package viewOther.adabters;

import java.util.Map;

import model.Cell;
import model.PlayerColor;
import model.ReadonlyPawnsBoard;
import viewOther.model.ICard;
import viewOther.model.ICell;
import viewOther.model.IPlayer;

/**
 * Converts cel to ICell. Used within model adapter.
 */
public class CELLtoICELL implements ICell, Cell {
  Cell adaptee;
  ReadonlyPawnsBoard chapiron;

  /**
   * Represents a Cell to ICell adapter.
   * @param adaptee to adapt off of.
   * @param model to read data from.
   */
  public CELLtoICELL(Cell adaptee, ReadonlyPawnsBoard model) {
    this.adaptee = adaptee;
    this.chapiron = model;
  }

  @Override
  public Map.Entry<Integer, Integer> getScore() {
    return adaptee.getScore();
  }

  @Override
  public void influence(PlayerColor influencer) {
    adaptee.influence(influencer);
  }

  @Override
  public int getPawns() {
    return adaptee.getPawns();
  }

  @Override
  public Cell copy() {
    return adaptee.copy();
  }

  @Override
  public PlayerColor getPlayerColor() {
    return adaptee.getPlayerColor();
  }

  @Override
  public boolean hasCard() {
    return adaptee.getPawns() < 0 && adaptee.getPlayerColor() != null;
  }

  @Override
  public boolean hasPawns() {
    return adaptee.getPawns() > 0;
  }

  @Override
  public int getPawnCount() {
    return adaptee.getPawns();
  }

  @Override
  public ICard getCard() {
    return null;
  }

  @Override
  public IPlayer getOwner() {
    return new IPlayertoPlayer(chapiron.getPlayer(adaptee.getPlayerColor()));
  }
}
