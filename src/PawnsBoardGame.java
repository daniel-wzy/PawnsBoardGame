import java.util.Random;

import controller.ReplicablePawnsBoardController;
import model.BasicPawnsBoard;
import model.IPawnsBoard;
import model.PlayerColor;
import playeractions.HumanPlayer;
import playeractions.MachinePlayer;
import playeractions.PlayerActions;
import strategy.ControlBoard;
import strategy.FillFirst;
import view.BasicPawnsBoardFrame;
import view.PawnsBoardGuiView;

/**
 * Creates a new pawns board game with GUI interface for testing.
 */
public final class PawnsBoardGame {

  /**
   * Runs new game of pawns board with 7 x 5 bord with no shuffle to test GUI view.
   * @param args to use.
   */
  public static void main(String[] args) {
    if (args.length < 4) {
      throw new IllegalArgumentException("Missing required arguments!");
    }
    String redDeckFile = args[0];
    String blueDeckFile = args[1];
    String redStrategy = args[2];
    String blueStrategy = args[3];



    IPawnsBoard model = new BasicPawnsBoard(redDeckFile,
            blueDeckFile,
            false, 5, new Random(0226), 7, 5
    );

    PawnsBoardGuiView viewRed = new BasicPawnsBoardFrame(model, PlayerColor.RED);
    PawnsBoardGuiView viewBlue = new BasicPawnsBoardFrame(model, PlayerColor.BLUE);

    PlayerActions player1 = string2Actions(redStrategy, PlayerColor.RED);
    PlayerActions player2 = string2Actions(blueStrategy, PlayerColor.BLUE);



    ReplicablePawnsBoardController controller1 = new ReplicablePawnsBoardController(model,
            viewRed, redDeckFile);
    ReplicablePawnsBoardController controller2 = new ReplicablePawnsBoardController(model,viewBlue,
            blueDeckFile);

    controller1.play(player1);
    controller2.play(player2);

    model.startGame();



  }

  private static PlayerActions string2Actions(String str, PlayerColor color) {
    if (str.equals("human")) {
      return new HumanPlayer(color);
    }
    else if (str.equals("fillfirst")) {
      return new MachinePlayer(color, new FillFirst());
    }
    else {
      return new MachinePlayer(color, new ControlBoard());
    }
  }
}