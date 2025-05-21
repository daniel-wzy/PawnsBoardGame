import java.io.File;
import java.util.Random;

import controller.PawnsBoardStubController;
import model.BasicPawnsBoard;
import model.IPawnsBoard;
import model.PlayerColor;
import view.BasicPawnsBoardFrame;
import view.PawnsBoardGuiView;

/**
 * Creates a new pawns board game with GUI interface for testing.
 */
public final class PawnsBoardGameOld {

  /**
   * Runs new game of pawns board with 7 x 5 bord with no shuffle to test GUI view.
   * @param args to use.
   */
  public static void main(String[] args) {
    IPawnsBoard model = new BasicPawnsBoard("deckConfigFiles" + File.separator + "example1.txt",
            "deckConfigFiles" + File.separator + "example1.txt",
            false, 5, new Random(0226), 7, 5
    );
    PawnsBoardGuiView viewRed = new BasicPawnsBoardFrame(model, PlayerColor.RED);
    PawnsBoardGuiView viewBlue = new BasicPawnsBoardFrame(model, PlayerColor.BLUE);

    PawnsBoardStubController controller = new PawnsBoardStubController(model, viewRed, viewBlue);
    controller.gameStart();



  }
}