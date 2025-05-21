package controller;

import java.awt.Point;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import model.IPawnsBoard;
import model.PlayerColor;
import playeractions.PlayerActions;
import view.PawnsBoardGuiView;

/**
 * This controller is meant to run a game from ones player's perspective, which is determined at
 * runtime.
 */
public class ReplicablePawnsBoardController implements ModelActions, PlayerActionListener {
  private IPawnsBoard model;
  private PawnsBoardGuiView view;
  private PlayerActions player;
  private final String deckData;

  /**
   * Creates a controller that can be used byeither player.
   * @param model to play off of.
   * @param view to display to.
   * @param deckFile to use for player.
   */
  public ReplicablePawnsBoardController(IPawnsBoard model, PawnsBoardGuiView view,
                                        String deckFile) {
    this.model = model;
    this.view = view;
    this.view.addClickListener(this);
    this.model.addTurnListener(this);
    this.model.addGameOverListener(this);

    try {
      this.deckData = new String(Files.readAllBytes(Paths.get(deckFile)), StandardCharsets.UTF_8);
    }
    catch (IOException e) {
      throw new IllegalArgumentException("IOException occurred!");
    }

  }

  /**
   * Starts the controller, enabling the player to start interacting.
   * @param player for this controller to represent.
   */
  public void play(PlayerActions player) {
    this.player = player;
    this.player.addListener(this);
    this.view.makeVisible();
    this.model.setDeckContents(deckData, this.player.getPlayerColor());
  }

  @Override
  public void handleNextTurn() {
    // TODO: Fix for player implementation when player impl is done.
    if (model.getTurn() == this.player.getPlayerColor()) {
      System.out.println("Next turn " + this.player.getPlayerColor());
      this.view.refresh();
      this.player.takeTurn(model);
      this.view.refresh();

    }
  }

  @Override
  public void handleGameOver() {
    this.view.refresh();
    Map.Entry<Integer, Integer> score = this.model.getScore();
    int redScore = score.getKey();
    int blueScore = score.getValue();
    String winner = "No one";
    if (redScore > blueScore) {
      winner = "Red";
    }
    else if (blueScore > redScore) {
      winner = "Blue";
    }

    this.view.displayMsg("Game Over! Winner: "
            + winner + " Red Score: " + redScore + " Blue Score: " + blueScore);

  }

  @Override
  public void handleCellClick(int row, int col, PlayerColor color) {
    this.model.setCellHighlighted(new Point(row, col), color);
    System.out.println("Highlighted cell at: " + row + ", " + col);
    this.view.refresh();
  }

  @Override
  public void handleHandClick(int col, PlayerColor color) {
    this.model.setHandHighlighted(col, color);
    System.out.println("Highlighted: " + col);
    this.view.refresh();
  }

  @Override
  public void confirmMove(PlayerColor color) {
    System.out.println("Confirm move: " + color);
    if (this.model.getTurn() != this.player.getPlayerColor()) {
      return;
    }
    int handIndx = this.model.getHandHighlighted(color);
    Point putTo = this.model.getCellHighlighted(color);
    if (handIndx >= 0 && putTo.x >= 0 && putTo.y >= 0) {
      try {
        this.model.makeMove(color, handIndx, putTo.y, putTo.x);
      }
      catch (Exception e) {
        System.out.println(handIndx + " " + putTo.x + " " + putTo.y);
        System.out.println(e);
        this.view.displayMsg("That move is invalid! Please place on a valid title.");
      }
    }
    else {
      this.view.displayMsg("You must select a hand and" +
              " cell to play on before confirming your move!");
    }
    forgetHighlighted();
    this.view.refresh();
  }

  /**
   * Helper method to forget the highlighted choices.
   */
  private void forgetHighlighted() {
    this.model.setCellHighlighted(new Point(-1, -1), this.player.getPlayerColor());
    this.model.setHandHighlighted(-1, this.player.getPlayerColor());
    this.view.refresh();
  }

  @Override
  public void confirmPass(PlayerColor color) {
    if (this.model.getTurn() != this.player.getPlayerColor()) {
      return;
    }
    this.player.getPlayerColor();
    this.model.pass(color);
    System.out.println("Confirm pass!");
    this.view.refresh();
  }

  @Override
  public void displayMessage(PlayerColor color, String message) {
    if (this.player.getPlayerColor() == color) {
      this.view.refresh();
      this.view.displayMsg(message);
    }
  }
}
