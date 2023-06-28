package cs3500.pa03.model;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class for the AiPlayer class. It tests the behavior of various methods in the AiPlayer
 * class.
 */
class AiPlayerTest {

  private AiPlayer aiPlayer;

  /**
   * Sets up the AiPlayer object with a player board and an AI board.
   */
  @BeforeEach
  public void setup() {
    aiPlayer = new AiPlayer();
    List<Coord> coord = new ArrayList<>();
    coord.add(new Coord(0, 0));
    coord.add(new Coord(0, 1));
    coord.add(new Coord(0, 2));
    Ship ship = new Ship(ShipType.SUBMARINE, coord);
    List<Ship> ships = new ArrayList<>();
    ships.add(ship);
    Board aiBoard = new AiBoard(4, 4, ships);
    aiPlayer.setBoard(aiBoard);
  }

  /**
   * Test case for the name method.
   * Asserts that the name of the AI player is null.
   */
  @Test
  void name() {
    assertEquals("Ai", aiPlayer.name());
  }

  /**
   * Test case for the takeShots method.
   * Asserts that the AI player takes exactly 1 shot.
   */
  @Test
  void takeShots() {
    List<Coord> result = aiPlayer.takeShotsFromServer();

    assertEquals(1, result.size());
  }

  /**
   * Test case for the reportDamage method.
   * Reports opponent shots and asserts that the AI player correctly identifies the damage
   * inflicted by the opponent.
   */
  @Test
  void reportDamage() {
    List<Coord> opponentShots = new ArrayList<>();
    opponentShots.add(new Coord(0, 0));
    opponentShots.add(new Coord(1, 1));
    opponentShots.add(new Coord(2, 2));

    List<Coord> expectedDamage = new ArrayList<>();
    expectedDamage.add(new Coord(0, 0));


    List<Coord> result = aiPlayer.reportDamage(opponentShots);

    assertEquals(expectedDamage, result);
  }
}