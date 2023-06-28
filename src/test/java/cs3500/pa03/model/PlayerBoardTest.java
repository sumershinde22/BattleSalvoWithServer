package cs3500.pa03.model;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class for the PlayerBoard class. It tests the behavior of various methods in the
 * PlayerBoard class.
 */
class PlayerBoardTest {
  public Board playerBoard;

  /**
   * Sets up the PlayerBoard object for testing.
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
    playerBoard = new PlayerBoard(4, 4, ships);
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
    array[0][0] = 'S';
    array[0][1] = 'S';
    array[0][2] = 'S';
    assertEquals(Arrays.deepToString(array), Arrays.deepToString(playerBoard.returnBoard()));
  }

  /**
   * Test case for the returnBoard method when a coordinate is hit.
   * Asserts that the returned board matches the expected board after a hit.
   */
  @Test
  public void testReturnHitBoard() {
    List<Coord> coordList = new ArrayList<>();
    coordList.add(new Coord(0, 0));
    playerBoard.updateShots(coordList);
    char[][] array = new char[4][4];
    for (char[] chars : array) {
      Arrays.fill(chars, '0');
    }
    array[0][0] = 'H';
    array[0][1] = 'S';
    array[0][2] = 'S';

    assertEquals(Arrays.deepToString(array), Arrays.deepToString(playerBoard.returnBoard()));
  }

  /**
   * Test case for the returnBoard method when a coordinate is missed.
   * Asserts that the returned board matches the expected board after a miss.
   */
  @Test
  public void testReturnMissBoard() {
    List<Coord> coordList = new ArrayList<>();
    coordList.add(new Coord(2, 2));
    playerBoard.updateShots(coordList);
    char[][] array = new char[4][4];
    for (char[] chars : array) {
      Arrays.fill(chars, '0');
    }
    array[0][0] = 'S';
    array[0][1] = 'S';
    array[0][2] = 'S';
    assertEquals(Arrays.deepToString(array), Arrays.deepToString(playerBoard.returnBoard()));
  }

  /**
   * Test case for the returnBoard method when a ship is sunk.
   * Asserts that the returned board matches the expected board after a ship is sunk.
   */
  @Test
  public void testReturnSunkBoard() {
    List<Coord> coordList = new ArrayList<>();
    coordList.add(new Coord(0, 0));
    coordList.add(new Coord(0, 1));
    coordList.add(new Coord(0, 2));
    coordList.add(new Coord(1, 0));
    coordList.add(new Coord(0, 3));
    playerBoard.updateShots(coordList);
    char[][] array = new char[4][4];
    for (char[] chars : array) {
      Arrays.fill(chars, '0');
    }
    array[0][0] = 'H';
    array[0][1] = 'H';
    array[0][2] = 'H';
    assertEquals(Arrays.deepToString(array), Arrays.deepToString(playerBoard.returnBoard()));
  }

  /**
   * Test case for the getShipNum method.
   * Asserts that the number of ships on the board matches the expected value.
   */
  @Test
  public void testGetNumShip() {
    assertEquals(1, playerBoard.getShipNum());
  }

  /**
   * Test case for the setCurrentShots method.
   * Asserts that the current shots on the board are correctly set.
   */
  @Test
  public void testSetCurrentShots() {
    List<Coord> coords = new ArrayList<>();
    coords.add(new Coord(0, 0));
    playerBoard.setCurrentShots(coords);
    assertEquals(List.of(new Coord(0, 0)), playerBoard.currentShots);
  }
}