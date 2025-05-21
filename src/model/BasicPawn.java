package model;

/**
 * Represents a pawn that can be a cell on the board. Used to model board interactions.
 * must have an owner that is not null.
 */
public class BasicPawn extends AbstractCell {
  private int pawns;

  /**
   * Creates a pawn cell, representing a pawn on the board.
   * @param color to claim ownership to the pawn.
   */
  public BasicPawn(PlayerColor color, int pawns) {
    super();
    this.pawns = pawns;
    this.owner = color;
  }

  /**
   * Creates a pawn cell, representing a pawn on the board without a color.
   */
  public BasicPawn() {
    super();
    this.pawns = 0;
  }


  @Override
  public void influence(PlayerColor influencer) {
    if ((this.owner == influencer || this.owner == null) && this.pawns < 3) {
      this.pawns++;
    }
    this.owner = influencer;
  }


  @Override
  public int getPawns() {
    return this.pawns;
  }

  @Override
  public String toString() {
    String color = "";
    if (this.owner == PlayerColor.RED) {
      color = "R";
    }
    else if (this.owner == PlayerColor.BLUE) {
      color = "B";
    }

    switch (this.pawns) {
      case 1:
        return "_*_"  + color;
      case 2:
        return "*_*" + color;
      case 3:
        return "***" + color;
      default:
        return "___" + color;
    }
  }

  @Override
  public Cell copy() {
    return new BasicPawn(this.owner, this.pawns);
  }

  @Override
  public PlayerColor getPlayerColor() {
    return this.owner;
  }
}
