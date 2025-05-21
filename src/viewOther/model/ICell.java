package viewOther.model;

/**
 * Interface representing one cell on board.
 */
public interface ICell {

  /**
   * Checks if the cell contains a card.
   */
  boolean hasCard();

  /**
   * Checks if the cell contains pawns.
   */
  boolean hasPawns();

  /**
   * Returns the number of pawns in this cell.
   */
  int getPawnCount();

  /**
   * Returns the card in this cell, if any.
   */
  ICard getCard();

  /**
   * Returns the player who owns the cell or null if unowned.
   */
  IPlayer getOwner();

}
