package controller;

import java.awt.Point;
import java.io.File;
import java.util.Random;

import model.BasicPawnsBoard;
import model.IPawnsBoard;
import model.PlayerColor;
import strategy.ControlBoard;
import strategy.Move;
import strategy.PawnsBoardStrategy;
import view.BasicPawnsBoardFrame;
import view.PawnsBoardGuiView;

/**
 * A controller that acts as a stub controller for the game. This controller aan either take in
 * a custom model, or create a basic testing model, and view, depending on constructor.
 * The controller handles mouse click events.
 */
public class PawnsBoardStubController implements PlayerActionListener {
  private IPawnsBoard model;
  private PawnsBoardGuiView view;
  private PawnsBoardGuiView view2;

  /**
   * Represents a controller that can be used to quickly test view with pre-specified model and
   * views.
   */
  public PawnsBoardStubController() {
    this.model = new BasicPawnsBoard("deckConfigFiles" +
            File.separator + "example1.txt",
            "deckConfigFiles" +
                    File.separator + "example1.txt",false, 5, new Random(0226),
            5, 3);
    this.view = new BasicPawnsBoardFrame(this.model, PlayerColor.RED);
    this.view.addClickListener(this);
    this.view2 = new BasicPawnsBoardFrame(this.model, PlayerColor.BLUE);
    this.view2.addClickListener(this);
  }

  /**
   * Represents a controller that takes in custom model, and two views. One representing red view,
   * and 2 representing the blue player view.
   * @param model to use for running game.
   * @param view to use for red player.
   * @param view2 to use for blue player.
   */
  public PawnsBoardStubController(IPawnsBoard model, PawnsBoardGuiView view,
                                  PawnsBoardGuiView view2) {
    this.model = model;
    this.view = view;
    this.view2 = view2;
    this.view.addClickListener(this);
    this.view2.addClickListener(this);
  }

  /**
   * Starts the game by making the views visible, and refreshing them.
   */
  public void gameStart() {
    view.makeVisible();
    view.refresh();
    view2.makeVisible();
    view2.refresh();
  }

  @Override
  public void handleCellClick(int row, int col, PlayerColor color) {
    this.model.setCellHighlighted(new Point(row, col), color);
    System.out.println("Highlighted cell at: " + row + ", " + col);
    view.refresh();
    view2.refresh();
  }

  @Override
  public void handleHandClick(int col, PlayerColor color) {
    this.model.setHandHighlighted(col, color);
    System.out.println("Highlighted: " + col);
    view.refresh();
    view2.refresh();
  }

  @Override
  public void confirmMove(PlayerColor color) {
    int handIndx = this.model.getHandHighlighted(color);
    Point putTo = this.model.getCellHighlighted(color);
    if (handIndx >= 0 && putTo.x >= 0 && putTo.y >= 0) {
      this.model.makeMove(color, handIndx, putTo.y, putTo.x);
    }
    System.out.println("Confirm move!");
    view.refresh();
    view2.refresh();
    PawnsBoardStrategy strat = new ControlBoard();
    Move stratMovw;
    if (PlayerColor.RED.equals(color)) {
      stratMovw = strat.chooseMove(this.model, this.model.getPlayer(PlayerColor.BLUE));
    }
    else {
      stratMovw = strat.chooseMove(this.model, this.model.getPlayer(PlayerColor.RED));
    }
    System.out.println(stratMovw.handindex + " " + stratMovw.r + " " + stratMovw.c);
  }

  @Override
  public void confirmPass(PlayerColor color) {
    this.model.pass(color);
    System.out.println("Confirm pass!");
  }

  @Override
  public void displayMessage(PlayerColor color, String message) {
    // Stub
  }
}

