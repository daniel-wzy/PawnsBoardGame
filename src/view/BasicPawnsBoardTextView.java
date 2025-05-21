package view;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import model.Cell;
import model.IPawnsBoard;

/**
 * Represents a basic textual view for a basic pawns board game.
 * Render each board eith blank spaces represented as: ___.
 * 1 pawn represented as _*_.
 * 2 pawns as *_*.
 * 3 pawns as ***.
 * with score flanking each row, red to right blue to left.
 * FOR TAs: To use this view create a new BasicPawnsBoard model,
 * and new BasicPawnsBoardTextualView.
 * Pass the model into the view and use the models methods to play the game.
 * run the render method with an appendable, such as system.out.
 */
public class BasicPawnsBoardTextView implements PawnsBoardTextView {
  IPawnsBoard model;

  /**
   * A Textual view to display the pawns board model.
   * @param model to use in displaying.
   */
  public BasicPawnsBoardTextView(IPawnsBoard model) {
    this.model = model;
  }

  @Override
  public void render(Appendable out) {
    List<Map.Entry<Integer, Integer>> scores = model.getScores();
    List<List<Cell>> board = model.getBoard();
    String output = "";
    for (int index = 0; index < board.size(); index++) {
      List<Cell> row = board.get(index);
      Map.Entry<Integer, Integer> entry = scores.get(index);
      String rowString = entry.getKey() + " ";
      for (Cell cell : row) {
        rowString += cell.toString() + " ";
      }
      rowString += entry.getValue() + "\n";
      output += rowString;
    }
    try {
      out.append(output + "\n");
    }
    catch (IOException e) {
      throw new IllegalStateException("IO Error Occured!");
    }
  }
}
