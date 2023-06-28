package cs3500.pa03.model;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class for the AiBoard class. It tests the behavior of various methods in the AiBoard
 * class.
 */
class AiBoardTest {
  private Board aiBoard;

  /**
   * Sets up the AiBoard object with a submarine ship.
   */
  @BeforeEach
  public void setup() {
    List<Coord> coord = new ArrayList<>();
    coord.add(new Coord(0, 0));
    coord.add(new Coord(0, 1));
    coord.add(new Coord(0, 2));
    Ship ship = new Ship(ShipType.SUBMARINE, coord);
    List<Ship> ships = new ArrayList<>();
    ships.add(ship);
    aiBoard = new AiBoard(4, 4, ships);
  }

  /**
   * Test case for the returnBoard method when the board is empty.
   * Asserts that the returned board matches the expected empty board.
   */

  @Test
  public void testReturnEmptyBoard() {
    char[][] array = new char[4][4];
    for (char[] chars : array) {
      Arrays.fill(chars, '0');
    }
    assertEquals(Arrays.deepToString(array), Arrays.deepToString(aiBoard.returnBoard()));
  }

  /**
   * Test case for the returnBoard method when a hit has been made on the board.
   * Updates the board with a hit at (0, 0) and asserts that the returned board matches the
   * expected board with the hit.
   */
  @Test
  public void testReturnHitBoard() {
    List<Coord> coordList = new ArrayList<>();
    coordList.add(new Coord(0, 0));
    aiBoard.updateShots(coordList);
    char[][] array = new char[4][4];
    for (char[] chars : array) {
      Arrays.fill(chars, '0');
    }
    array[0][0] = 'H';
    assertEquals(Arrays.deepToString(array), Arrays.deepToString(aiBoard.returnBoard()));
  }

  /**
   * Test case for the returnBoard method when a miss has been made on the board.
   * Updates the board with a miss at (2, 2) and asserts that the returned board matches the
   * expected board with the miss.
   */
  @Test
  public void testReturnMissBoard() {
    List<Coord> coordList = new ArrayList<>();
    coordList.add(new Coord(2, 2));
    aiBoard.updateShots(coordList);
    char[][] array = new char[4][4];
    for (char[] chars : array) {
      Arrays.fill(chars, '0');
    }
    array[2][2] = 'M';
    assertEquals(Arrays.deepToString(array), Arrays.deepToString(aiBoard.returnBoard()));
  }

  /**
   * Test case for the returnBoard method when a ship has been sunk on the board.
   * Updates the board with multiple hits and misses to sink a ship, and asserts that the returned
   * board matches the expected board with the sunk ship.
   */
  @Test
  public void testReturnSunkBoard() {
    List<Coord> coordList = new ArrayList<>();
    coordList.add(new Coord(0, 0));
    coordList.add(new Coord(0, 1));
    coordList.add(new Coord(0, 2));
    coordList.add(new Coord(1, 0));
    coordList.add(new Coord(0, 3));
    aiBoard.updateShots(coordList);
    char[][] array = new char[4][4];
    for (char[] chars : array) {
      Arrays.fill(chars, '0');
    }
    array[0][0] = 'H';
    array[0][1] = 'H';
    array[0][2] = 'H';
    array[1][0] = 'M';
    array[0][3] = 'M';
    assertEquals(Arrays.deepToString(array), Arrays.deepToString(aiBoard.returnBoard()));
  }

  /**
   * Test case for the getShipNum method.
   * Asserts that the number of ships on the board matches the expected value.
   */
  @Test
  public void testGetNumShip() {
    assertEquals(1, aiBoard.getShipNum());
  }

  /**
   * Test case for the setCurrentShots method.
   * Sets the current shots on the board and asserts that the current shots list matches the
   * expected list.
   */
  @Test
  public void testSetCurrentShots() {
    List<Coord> coords = new ArrayList<>();
    coords.add(new Coord(0, 0));
    aiBoard.setCurrentShots(coords);
    assertEquals(List.of(new Coord(0, 0)), aiBoard.currentShots);
  }

}