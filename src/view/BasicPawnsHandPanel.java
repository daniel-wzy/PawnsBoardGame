package view;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.awt.Point;
import java.awt.Graphics2D;
import java.awt.Graphics;


import controller.PlayerActionListener;
import model.Card;
import model.PlayerColor;
import model.ReadonlyPawnsBoard;

/**
 * Represents the hand panel containing players cards. Placed at the bottom of a frame,
 * and contains cards of the associated players hand. This panel
 * changes depending on which player the frame represents.
 */
public class BasicPawnsHandPanel extends AbstractPawnsBoardPanel {
  private List<Card> hand;

  /**
   * Creates a new pawns hand panel with model and color specified.
   * @param model to create hand panel from.
   * @param color of player to create hand panel for.
   */
  public BasicPawnsHandPanel(ReadonlyPawnsBoard model, PlayerColor color) {
    super(model, color);
    updateHand();
  }

  /**
   * Helper method to update the associated hand this view represents.
   */
  private void updateHand() {
    if (this.color == PlayerColor.RED) {
      this.hand = this.model.getRedHand();
    }
    else {
      this.hand = this.model.getBlueHand();
    }
  }

  /**
   * Inits this hand component, drawing cards based on the players current hand.
   * @param g2d to draw cards on to.
   */
  private void init(Graphics2D g2d) {
    updateHand();
    int cellWidthSize = Math.round(this.getWidth() / this.hand.size());
    int cellHeightSize = 200;

    g2d.setColor(playerColor);
    g2d.fillRect(0, 0, this.getWidth(), 200);
    for (int i = 0; i < this.hand.size(); i++) {
      renderCard(i * cellWidthSize, 0, this.hand.get(i),
              cellWidthSize, cellHeightSize, g2d, false, 0, i);
    }
  }

  @Override
  public Point pixelToModel(int x, int y) {
    // TODO: Add support for different views
    int cellWidthSize = Math.round(this.getWidth() / this.hand.size());
    int cellHeightSize = Math.round(this.getHeight());
    int row = (int) Math.floor(x / cellWidthSize);
    int col = (int) Math.floor(y / cellHeightSize);
    return new Point(row, col);
  }

  @Override
  public void addClickListener(PlayerActionListener listenerHand) {
    this.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        Point modelCords = pixelToModel(e.getX(), e.getY());
        listenerHand.handleHandClick(modelCords.x, color);
      }
    });
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D)g.create();

    init(g2d);
  }
}
