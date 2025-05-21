package viewOther.adabters;

import java.awt.Point;

import controller.PlayerActionListener;
import model.PlayerColor;
import viewOther.IGUIView;
import viewOther.controller.Features;
import viewOther.model.ReadonlyPawnsGame;

/**
 * Adapts features listerner to Player Actions listerner. Used with controller.
 */
public class FeaturesToPlayerActionsL implements Features {
  PlayerActionListener adaptee;
  PlayerColor perspective;
  IGUIView view;
  ReadonlyPawnsGame model;

  /**
   * Adapter to addapt features to player actions.
   * @param adaptee to adapt off of.
   * @param perspective to represent.
   * @param view to attach to.
   * @param model to view.
   */
  public FeaturesToPlayerActionsL(PlayerActionListener adaptee, PlayerColor perspective,
                                  IGUIView view, ReadonlyPawnsGame model) {
    this.adaptee = adaptee;
    this.model = model;
    this.perspective = perspective;
    this.view = view;
  }

  @Override
  public void onCardSelected(int cardIdx) {
    int index = pixelToHandModel(cardIdx);
    this.adaptee.handleHandClick(index, perspective);
    this.view.highlightCard(index);
  }

  @Override
  public void onCellSelected(int row, int col) {
    System.out.println("row: " + row + ", col: " + col);
    Point modelCords = pixelToModel(col, row);
    this.adaptee.handleCellClick(modelCords.y, modelCords.x, perspective);
    this.view.highlightCell(modelCords.x, modelCords.y);
  }

  @Override
  public void onConfirm() {
    this.adaptee.confirmMove(perspective);
  }

  @Override
  public void onPass() {
    this.adaptee.confirmPass(perspective);
  }

  /**
   * Converts pixel cordinates to model cordinates.
   * @param row in pixels of the cell.
   * @param col column in pixels of the cell.
   * @return a point represents the row x and column y of the model points.
   */
  private Point pixelToModel(int row, int col) {
    int leftPadding = 100;
    int cellHeight = this.view.getBoardHeight() / this.model.getNumRows();
    int cellWidth = (this.view.getBoardWidth() - 200) / this.model.getNumCols();

    int correctedCol = col - leftPadding;

    return new Point((row / cellHeight),
            ((int)  correctedCol / cellWidth));
  }

  /**
   * Converts pixels to hand model index.
   * @param index in pixels to convert.
   * @return an int with model cords.
   */
  private int pixelToHandModel(int index) {
    viewOther.model.PlayerColor color;
    if (perspective == PlayerColor.RED) {
      color = viewOther.model.PlayerColor.RED;
    }
    else {
      color = viewOther.model.PlayerColor.BLUE;
    }
    int cellWidth = this.view.getHandWidth() / this.model.getPlayerHand(color).size();
    return index / cellWidth;
  }
}
