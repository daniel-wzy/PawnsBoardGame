import java.io.File;
import java.util.Random;

import model.BasicPawnsBoard;
import model.IPawnsBoard;
import model.PlayerColor;
import view.BasicPawnsBoardTextView;
import view.PawnsBoardTextView;



/**
 * Represents main pawnsboard class to run project from.
 * FOR TAs: Run the main method from this class,
 * and a simulation of the model will run, with a visual rep in console.
 */
public class PawnsBoard {
  /**
   * Represents main method to run simulated pawnsboard game.
   * @param args to input.
   */
  public static void main(String[] args) {
    IPawnsBoard game = new BasicPawnsBoard("deckConfigFiles" + File.separator + "example1.txt",
            "deckConfigFiles" + File.separator + "example1.txt",
            false, 5, new Random(0226), 5, 3
            );

    PawnsBoardTextView view = new BasicPawnsBoardTextView(game);
    view.render(System.out);
    game.makeMove(PlayerColor.RED, 0, 0, 0);
    view.render(System.out);
    game.makeMove(PlayerColor.BLUE, 0, 0, 4);
    view.render(System.out);
    game.makeMove(PlayerColor.RED, 0, 0, 1);
    view.render(System.out);
    game.makeMove(PlayerColor.BLUE, 0, 0, 3);
    view.render(System.out);
    game.makeMove(PlayerColor.RED, 0, 1, 0);
    view.render(System.out);
    game.makeMove(PlayerColor.BLUE, 0, 1, 4);
    view.render(System.out);
    game.makeMove(PlayerColor.RED, 0, 2, 0);
    view.render(System.out);
    game.makeMove(PlayerColor.BLUE, 0, 2, 4);
    view.render(System.out);
    game.makeMove(PlayerColor.RED, 0, 1, 1);
    view.render(System.out);
    game.makeMove(PlayerColor.BLUE, 0, 1, 3);
    view.render(System.out);
    game.pass(PlayerColor.RED);
    view.render(System.out);
    game.makeMove(PlayerColor.BLUE, 0, 2, 3);
    view.render(System.out);
    game.pass(PlayerColor.RED);
    view.render(System.out);
    game.makeMove(PlayerColor.BLUE, 0, 1, 2);
    view.render(System.out);
    game.makeMove(PlayerColor.BLUE, 0, 0, 2);
    view.render(System.out);
    game.pass(PlayerColor.RED);
    view.render(System.out);
    game.makeMove(PlayerColor.BLUE, 0, 2, 2);
    view.render(System.out);
    game.pass(PlayerColor.RED);
    view.render(System.out);
    game.makeMove(PlayerColor.BLUE, 0, 2, 1);
    view.render(System.out);
    game.pass(PlayerColor.RED);
    view.render(System.out);
    game.makeMove(PlayerColor.BLUE, 0, 0, 2);
    view.render(System.out);
  }
}