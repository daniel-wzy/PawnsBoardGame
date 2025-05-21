package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JOptionPane;

import controller.PlayerActionListener;
import model.PlayerColor;
import model.ReadonlyPawnsBoard;

/**
 * Represents a frame that holds all Pawnsboard components and panels. Implements pawns board GUI
 * interface, and is a JFrame.
 */
public class BasicPawnsBoardFrame extends JFrame implements PawnsBoardGuiView {
  private BasicPawnsBoardPanel panel;
  private BasicPawnsHandPanel handPanel;

  private final PlayerColor color;

  /**
   * Creates a pawnsboard frame that holds all game visualization information.
   * @param model to read from.
   * @param color of the player this frame represents.
   */
  public BasicPawnsBoardFrame(ReadonlyPawnsBoard model, PlayerColor color) {
    super();
    BasicPawnsScoreColumn redScorePanel;
    BasicPawnsScoreColumn blueScorePanel;

    this.color = color;

    setSize(800, 600);
    setDefaultCloseOperation(EXIT_ON_CLOSE);

    panel = new BasicPawnsBoardPanel(model, this.color);
    handPanel = new BasicPawnsHandPanel(model, this.color);
    redScorePanel = new BasicPawnsScoreColumn(model, PlayerColor.RED);
    blueScorePanel = new BasicPawnsScoreColumn(model, PlayerColor.BLUE);
    setLayout(new BorderLayout());

    this.add(panel, BorderLayout.CENTER);

    // Inits hand container.
    JPanel handContainer = new JPanel(new BorderLayout());
    handContainer.setPreferredSize(new Dimension(800, 200));
    handContainer.add(handPanel, BorderLayout.CENTER);
    this.add(handContainer, BorderLayout.SOUTH);

    // Inits red score container.
    JPanel redScoreContainer = new JPanel(new BorderLayout());
    redScoreContainer.setPreferredSize(new Dimension(100, this.getHeight() - 200));
    redScoreContainer.add(redScorePanel, BorderLayout.CENTER);
    this.add(redScoreContainer, BorderLayout.WEST);

    // Inits blue score container.
    JPanel blueScoreContainer = new JPanel(new BorderLayout());
    blueScoreContainer.setPreferredSize(new Dimension(100, this.getHeight() - 200));
    blueScoreContainer.add(blueScorePanel, BorderLayout.CENTER);
    this.add(blueScoreContainer, BorderLayout.EAST);

  }

  @Override
  public void addClickListener(PlayerActionListener listener) {
    this.panel.addClickListener(listener);
    this.handPanel.addClickListener(listener);

    this.addKeyListener(new KeyListener() {
      @Override
      public void keyTyped(KeyEvent e) {
        char keyChar = e.getKeyChar();
        if (keyChar == 'm') {
          listener.confirmMove(color);
        }
        else if (keyChar == 'p') {
          listener.confirmPass(color);
        }
      }

      @Override
      public void keyPressed(KeyEvent e) {
        // Does nothing yet, does not need to read key pressed.
      }

      @Override
      public void keyReleased(KeyEvent e) {
        // Does nothing yet, does not need to read key released.
      }
    });
  }

  @Override
  public void refresh() {
    this.repaint();
  }

  @Override
  public void makeVisible() {
    setVisible(true);
  }

  @Override
  public void displayMsg(String msg) {
    JOptionPane.showMessageDialog(this, msg);
  }
}
