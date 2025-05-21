package viewOther;

import viewOther.model.ICard;
import viewOther.model.PlayerColor;
import viewOther.model.ReadonlyPawnsGame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;
import javax.swing.JPanel;

/**
 * A JPanel that displays the current player's hand of cards.
 */
public class HandPanel extends JPanel implements IHandPanel {

  private final ReadonlyPawnsGame model;
  private final ViewHelper delegate;
  protected int selectedCard = -1;
  protected final PlayerColor perspective;

  /**
   * Constructs a HandPanel for a given player's hand.
   *
   * @param model read-only model representing the current game state
   * @param perspective player whose hand is being displayed
   */
  public HandPanel(ReadonlyPawnsGame model, PlayerColor perspective) {
    this.model = model;
    this.perspective = perspective;
    this.delegate = new ViewHelper(model);
    this.setPreferredSize(new Dimension(600, 150));
    this.setBackground(Color.LIGHT_GRAY);
    clearCardHighlight();
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    drawHand(g);
  }

  @Override
  public void drawHand(Graphics g) {
    List<ICard> hand = model.getPlayerHand(perspective);
    int count = hand.size();

    int cardWidth = getWidth() / Math.max(count, 1);
    int cardHeight = getHeight();

    for (int i = 0; i < count; i++) {
      int x = i * cardWidth;
      int y = 0;

      ICard card = hand.get(i);
      delegate.renderCard(g, card, this.perspective, x, y, cardWidth, cardHeight);

      if (i == selectedCard) {
        Graphics2D g2d = (Graphics2D) g;
        Color highlight = new Color(0, 255, 255, 100);
        g2d.setColor(highlight);
        g2d.fillRoundRect(x, y, cardWidth, cardHeight, 15, 15);
      }
    }
  }

  @Override
  public void highlightCard(int cardIndex) {
    if (cardIndex == selectedCard) {
      clearCardHighlight(); // deselect
    } else {
      selectedCard = cardIndex;
      repaint();
    }
  }

  @Override
  public void clearCardHighlight() {
    this.selectedCard = -1;
    repaint();
  }
}
