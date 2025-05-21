package strategy;

/**
 * The class that will make the move in strategies.
 */
public class Move {
  public final int handindex;
  public final int r;
  public final int c;

  /**
   * Constructs a move that requires the card index, row number, and column number.
   *
   * @param handindex the index of the card
   * @param r         the row that is placed in
   * @param c         the column that is placed in
   */
  public Move(int handindex, int r, int c) {
    this.handindex = handindex;
    this.r = r;
    this.c = c;
  }
}
