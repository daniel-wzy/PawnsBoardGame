package model;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Tests for basic pawns.
 */
public class BasicPawnTest {

  private BasicPawn redPawn;
  private BasicPawn bluePawn;
  private BasicPawn emptyPawn;

  // Initialize pawns
  @Before
  public void setUp() {
    redPawn = new BasicPawn(PlayerColor.RED, 1);
    bluePawn = new BasicPawn(PlayerColor.BLUE, 2);
    emptyPawn = new BasicPawn();
  }

  // Test constructor with a given owner and number of pawns
  @Test
  public void testConstructorWithColorAndPawns() {
    assertEquals(PlayerColor.RED, redPawn.getPlayerColor());
    assertEquals(1, redPawn.getPawns());

    assertEquals(PlayerColor.BLUE, bluePawn.getPlayerColor());
    assertEquals(2, bluePawn.getPawns());
  }

  // Test default constructor
  @Test
  public void testDefaultConstructor() {
    assertNull(emptyPawn.getPlayerColor());
    assertEquals(0, emptyPawn.getPawns());
  }

  // Test influence method
  @Test
  public void testInfluence() {
    // red pawn influences empty pawn
    emptyPawn.influence(PlayerColor.RED);
    assertEquals(1, emptyPawn.getPawns());
    assertEquals(PlayerColor.RED, emptyPawn.getPlayerColor());

    emptyPawn.influence(PlayerColor.RED);
    emptyPawn.influence(PlayerColor.RED);
    emptyPawn.influence(PlayerColor.RED);
    assertEquals(3, emptyPawn.getPawns());

    // blue pawn influences a red pawn
    bluePawn.influence(PlayerColor.RED);
    assertEquals(2, bluePawn.getPawns());
    assertEquals(PlayerColor.RED, redPawn.getPlayerColor());
  }

  // Test if getPawns returns the correct number of pawns
  @Test
  public void testGetPawns() {
    assertEquals(1, redPawn.getPawns());
    assertEquals(2, bluePawn.getPawns());
    assertEquals(0, emptyPawn.getPawns());
  }

  // Test toString method
  @Test
  public void testToString() {
    assertEquals("_*_R", redPawn.toString());
    assertEquals("*_*B", bluePawn.toString());
    assertEquals("___", emptyPawn.toString());
  }
}
