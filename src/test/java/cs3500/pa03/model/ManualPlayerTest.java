package cs3500.pa03.model;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class for the ManualPlayer class. It tests the behavior of various methods in the
 * ManualPlayer class.
 */
class ManualPlayerTest {
  private ManualPlayer manualPlayer;

  /**
   * Sets up the ManualPlayer object for testing.
   */
  @BeforeEach
  public void setUp() {
    manualPlayer = new ManualPlayer();

    List<Coord> coord = new ArrayList<>();
    coord.add(new Coord(0, 0));
    coord.add(new Coord(0, 1));
    coord.add(new Coord(0, 2));
    Ship ship = new Ship(ShipType.SUBMARINE, coord);
    List<Ship> ships = new ArrayList<>();
    ships.add(ship);
    Board playerBoard = new PlayerBoard(4, 4, ships);
    manualPlayer.setBoard(playerBoard);
  }

  /**
   * Test case for the name method.
   */
  @Test
  public void name() {
    assertNull(manualPlayer.name());
  }

  /**
   * Test case for the takeShots method.
   * Asserts that the manual player returns the expected shots.
   */
  @Test
  public void takeShots() {
    List<Coord> expectedShots = new ArrayList<>();
    expectedShots.add(new Coord(0, 0));
    expectedShots.add(new Coord(1, 1));
    expectedShots.add(new Coord(2, 2));
    manualPlayer.board.currentShots = expectedShots;

    List<Coord> result = manualPlayer.takeShots();

    assertEquals(expectedShots, result);
  }

  /**
   * Test case for the reportDamage method.
   * Asserts that the manual player correctly reports the damaged coordinates.
   */
  @Test
  public void reportDamage() {
    List<Coord> opponentShots = new ArrayList<>();
    opponentShots.add(new Coord(0, 0));
    opponentShots.add(new Coord(1, 1));
    opponentShots.add(new Coord(2, 2));

    List<Coord> expectedDamage = new ArrayList<>();
    expectedDamage.add(new Coord(0, 0));


    List<Coord> result = manualPlayer.reportDamage(opponentShots);

    assertEquals(expectedDamage, result);
  }
}