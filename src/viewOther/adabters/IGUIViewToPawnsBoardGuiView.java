package viewOther.adabters;

import javax.swing.*;

import controller.PlayerActionListener;
import model.PlayerColor;
import model.ReadonlyPawnsBoard;
import view.PawnsBoardGuiView;
import viewOther.IGUIView;
import viewOther.PawnsBoardGUIView;
import viewOther.controller.Features;
import viewOther.model.ReadonlyPawnsGame;

/**
 * Adapts a IGUI View to PawnsBoardGuiView viwew.
 */
public class IGUIViewToPawnsBoardGuiView implements PawnsBoardGuiView {
  IGUIView adaptee;
  PlayerColor color;
  ReadonlyPawnsGame model;

  /**
   * Represents a adapter to adapt from IGUI view to Pawns board.
   * @param model to adapt off of.
   * @param color to represent view.
   */
  public IGUIViewToPawnsBoardGuiView(ReadonlyPawnsBoard model, PlayerColor color) {
    viewOther.model.PlayerColor perspective;

    if (color == PlayerColor.RED) {
      perspective = viewOther.model.PlayerColor.RED;
    }
    else {
      perspective = viewOther.model.PlayerColor.BLUE;
    }
    ReadonlyPawnsGame convertedModel = new PBToPG(model, color);
    this.color = color;

    this.adaptee = new PawnsBoardGUIView(convertedModel, perspective);
    this.model = convertedModel;
  }

  @Override
  public void addClickListener(PlayerActionListener listener) {
    Features convertedListener;
    convertedListener = new FeaturesToPlayerActionsL(listener, color,
            this.adaptee, this.model);
    this.adaptee.addFeatures(convertedListener);
  }

  @Override
  public void refresh() {
    this.adaptee.refresh();
  }

  @Override
  public void makeVisible() {
    this.adaptee.makeVisible();

  }

  @Override
  public void displayMsg(String msg) {
    JOptionPane.showMessageDialog(this.adaptee.getComponent(), msg);
  }
}
