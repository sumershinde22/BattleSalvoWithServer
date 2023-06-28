package cs3500.pa03.model;


import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class for the ShipType enum. It tests the behavior of various methods in the ShipType
 * enum.
 */
class ShipTypeTest {

  public ShipType shipType;

  /**
   * Sets up the ShipType object for testing.
   */
  @BeforeEach
  public void setup() {
    shipType = ShipType.CARRIER;
  }

  /**
   * Test case for the getCharForm method.
   * Asserts that the character representation of the ship type matches the expected value.
   */
  @Test
  public void testGetCharForm() {
    assertEquals('C', shipType.getCharForm());
  }

  /**
   * Test case for the getSize method.
   * Asserts that the size of the ship type matches the expected value.
   */
  @Test
  public void testGetSize() {
    assertEquals(6, shipType.getSize());
  }
}