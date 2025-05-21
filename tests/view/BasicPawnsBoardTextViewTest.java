package view;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Random;

import model.BasicPawnsBoard;
import model.IPawnsBoard;
import model.PlayerColor;

import static org.junit.Assert.assertEquals;

/**
 * Tests the basic pawns board textual view. Used to contiain
 * all tests.
 */
public class BasicPawnsBoardTextViewTest {
  IPawnsBoard pawnsBoard;
  PawnsBoardTextView pawnsBoardTextView;

  // Setup.
  @Before
  public void setUp() {
    pawnsBoard = new BasicPawnsBoard("deckConfigFiles" +
            File.separator + "example1.txt",
            "deckConfigFiles" +
                    File.separator + "example1.txt",
            false, 5, new Random(0226), 5, 3);
    pawnsBoardTextView = new BasicPawnsBoardTextView(pawnsBoard);
  }

  // Test creating a blank board.
  @Test
  public void testBlankBoard() {
    setUp();
    Appendable out = new StringBuilder();
    pawnsBoardTextView.render(out);
    String string = "0 _*_R ___ ___ ___ _*_B 0\n" +
            "0 _*_R ___ ___ ___ _*_B 0\n" +
            "0 _*_R ___ ___ ___ _*_B 0\n\n";
    assertEquals(string, out.toString());
  }

  // Tests playing a game.
  @Test
  public void testPlayGame() {
    setUp();
    pawnsBoard.makeMove(PlayerColor.RED, 0, 0, 0);
    Appendable out = new StringBuilder();
    pawnsBoardTextView.render(out);
    String string = "67 067R _*_R ___ ___ _*_B 0\n" +
            "0 *_*R ___ ___ ___ _*_B 0\n" +
            "0 _*_R ___ ___ ___ _*_B 0\n\n";
    assertEquals(string, out.toString());
    pawnsBoard.makeMove(PlayerColor.BLUE, 0, 0, 4);
    out = new StringBuilder();
    pawnsBoardTextView.render(out);
    string = "67 067R _*_R ___ _*_B 067B 67\n" +
            "0 *_*R ___ ___ ___ *_*B 0\n" +
            "0 _*_R ___ ___ ___ _*_B 0\n\n";
    assertEquals(string, out.toString());
    pawnsBoard.makeMove(PlayerColor.RED, 0, 0, 1);
    out = new StringBuilder();
    pawnsBoardTextView.render(out);
    string = "68 067R 001R ___ _*_B 067B 67\n" +
            "0 *_*R ___ ___ ___ *_*B 0\n" +
            "0 _*_R _*_R ___ ___ _*_B 0\n\n";
    assertEquals(string, out.toString());
    pawnsBoard.makeMove(PlayerColor.BLUE, 0, 0, 3);
    out = new StringBuilder();
    pawnsBoardTextView.render(out);
    string = "68 067R 001R ___ 001B 067B 68\n" +
            "0 *_*R ___ ___ ___ *_*B 0\n" +
            "0 _*_R _*_R ___ _*_B _*_B 0\n\n";
    assertEquals(string, out.toString());
  }
}
