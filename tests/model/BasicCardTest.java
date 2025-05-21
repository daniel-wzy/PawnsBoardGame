package model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

/**
 * Represents test for the basic card class.
 */
public class BasicCardTest {
  Card exampleCard1;

  // Tests basic card construction and method functionality.
  @Test
  public void testBasicCard() {
    assertThrows(IllegalArgumentException.class, () ->
            new BasicCard(null, 1, 1, new ArrayList<>(), PlayerColor.RED));
    assertThrows(IllegalArgumentException.class, () ->
            new BasicCard("Test card", 1, 1, null, PlayerColor.RED));
    assertThrows(IllegalArgumentException.class, () ->
            new BasicCard("test card", -1, 1, new ArrayList<>(), PlayerColor.RED));
    assertThrows(IllegalArgumentException.class, () ->
            new BasicCard("test card", 1, -1, new ArrayList<>(), PlayerColor.RED));


    List<Map.Entry<Integer, Integer>> exampleList = new ArrayList<>();
    exampleList.add(Map.entry(1, 1));
    exampleList.add(Map.entry(-1, -1));
    exampleList.add(Map.entry(0, -1));
    exampleCard1 = new BasicCard("test card", 1, 2, exampleList, PlayerColor.RED);
    assertEquals(1, exampleCard1.getCost());
    assertEquals(2, exampleCard1.getValue());
    assertEquals("test card", exampleCard1.getName());
    assertEquals(exampleList, exampleCard1.getInfluence());

  }
}