package cs3500.pa03.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class for the GameImpl class. It tests the behavior of various methods in the
 * GameImpl class.
 */
class GameImplTest {

  private GameImpl gameImpl;

  /**
   * Sets up the GameImpl object for testing.
   */
  @BeforeEach
  public void setUp() {
    gameImpl = new GameImpl();
  }

  /**
   * Test case for the setUpBoard method.
   * Asserts that the player board is correctly set up with the specified number of submarines.
   * Also asserts that the AI board is initialized with all '0' values.
   */
  @Test
  public void setUpBoard() {
    Map<ShipType, Integer> shipsNums = new HashMap<>();
    shipsNums.put(ShipType.SUBMARINE, 1);
    List<char[][]> outputArrays = gameImpl.setUpBoard(4, 4, shipsNums);
    int count = 0;
    char[][] playerBoard = outputArrays.get(0);

    for (char[] row : playerBoard) {
      for (char element : row) {
        if (element == 'S') {
          count++;
        }
      }
    }
    assertEquals(count, 3);

    char[][] array = new char[4][4];
    for (char[] chars : array) {
      Arrays.fill(chars, '0');
    }
    char[][] aiBoard = outputArrays.get(1);
    assertEquals(Arrays.deepToString(array), Arrays.deepToString(aiBoard));
  }
}

