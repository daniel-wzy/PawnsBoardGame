package viewOther;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;

import viewOther.controller.Features;
import viewOther.model.PlayerColor;
import viewOther.model.ReadonlyPawnsGame;


/**
 * Simple GUI view for the Pawns Board game.
 */
public class PawnsBoardGUIView extends JFrame implements IGUIView {
  private final BoardPanel boardPanel;
  private final HandPanel handPanel;


  /**
   * Constructs a GUI view for the game using the given model and player perspective.
   *
   * @param model the read-only model representing the current state of the game
   * @param perspective the perspective (RED or BLUE) from which the player views the game
   * @throws IllegalArgumentException if model or perspective is null
   */
  public PawnsBoardGUIView(ReadonlyPawnsGame model, PlayerColor perspective) {
    super("Pawns Board Game Player: " + perspective);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setPreferredSize(new Dimension(1000, 800));
    this.setLayout(new BorderLayout());

    // Panels
    this.boardPanel = new BoardPanel(model);
    this.handPanel = new HandPanel(model, perspective);

    this.add(boardPanel, BorderLayout.CENTER);
    this.add(handPanel, BorderLayout.SOUTH);

    this.pack();
  }

  @Override
  public void resetFocus() {
    this.setFocusable(true);
    this.requestFocusInWindow();
  }

  @Override
  public void setTitle(String title) {
    super.setTitle(title);
  }

  @Override
  public void makeVisible() {
    this.setVisible(true);
  }

  @Override
  public void highlightCard(int cardIndex) {
    handPanel.highlightCard(cardIndex);
  }

  @Override
  public void highlightCell(int row, int col) {
    boardPanel.highlightCell(row, col);
  }

  @Override
  public int getHandWidth() {
    return handPanel.getWidth();
  }

  @Override
  public int getBoardHeight() {
    return boardPanel.getHeight();
  }

  @Override
  public int getBoardWidth() {
    return boardPanel.getWidth();
  }

  @Override
  public void addFeatures(Features f) {
    // Mouse listener for board panel
    boardPanel.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        f.onCellSelected(e.getX(), e.getY());
      }
    });

    // Mouse listener for hand panel
    handPanel.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        f.onCardSelected(e.getX());
      }
    });

    // Key listener for confirm/pass
    this.addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
          f.onConfirm();
        } else if (e.getKeyCode() == KeyEvent.VK_P) {
          f.onPass();
        }
      }
    });
  }

  @Override
  public Component getComponent() {
    return this;
  }

  @Override
  public void refresh() {
    boardPanel.repaint();
    handPanel.repaint();
  }
}
