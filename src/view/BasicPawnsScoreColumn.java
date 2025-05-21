package view;

import java.util.List;
import java.util.Map;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Color;

import model.PlayerColor;
import model.ReadonlyPawnsBoard;

/**
 * Represents a score column panel used to display row by row scores. This panel will only
 * display the scores of the player that the parent frame is associated to.
 */
public class BasicPawnsScoreColumn extends AbstractPawnsBoardPanel {
  private final PlayerColor color;

  /**
   * Creates a new pawns score column given a model and associated player color.
   * @param model to base scores off of.
   * @param playerColor to associate this score panel to.
   */
  public BasicPawnsScoreColumn(ReadonlyPawnsBoard model, PlayerColor playerColor) {
    super(model,  playerColor);
    this.color = playerColor;
  }

  /**
   * Inits this score panel, drawing scores and lines.
   * @param g2d to draw scores on to.
   */
  private void init(Graphics2D g2d) {
    g2d.setColor(SCORETILES);
    g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
    g2d.setColor(Color.BLACK);
    int cellHeight = this.getHeight() / this.model.getHeight();

    List<Map.Entry<Integer, Integer>> rowScores = this.model.getScores();

    for (int row = 0; row < this.model.getHeight(); row++) {
      g2d.drawLine(0, row * cellHeight, this.getWidth(), row * cellHeight);
      int score;
      if (this.color == PlayerColor.RED) {
        score = rowScores.get(row).getKey();
      }
      else {
        score = rowScores.get(row).getValue();
      }
      g2d.setFont(g2d.getFont().deriveFont(40f));
      g2d.drawString(Integer.toString(score), 40, row * cellHeight + 40);
      g2d.setFont(g2d.getFont().deriveFont(10f));
    }
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D)g.create();

    init(g2d);
  }

}
