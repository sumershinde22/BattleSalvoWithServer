package cs3500.pa03.view;


import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa03.model.Coord;
import cs3500.pa03.model.GameResult;
import cs3500.pa03.model.ShipType;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class for the ViewImpl class. It tests the behavior of various methods in the ViewImpl
 * class.
 */
public class ViewImplTest {

  private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
  private final PrintStream originalOut = System.out;
  private final InputStream originalIn = System.in;

  /**
   * Sets up the output stream to capture system output.
   */
  @BeforeEach
  public void setUpStreams() {
    System.setOut(new PrintStream(outputStream));
  }

  /**
   * Restores the original system output and input streams.
   */

  @AfterEach
  public void restoreStreams() {
    System.setOut(originalOut);
    System.setIn(originalIn);
  }

  /**
   * Test case for the welcomeMessage method with valid input.
   * Asserts that the returned array and the printed welcome message match the expected values.
   */
  @Test
  public void testWelcomeMessageValidInput() {
    String testInput = "8 8\n";
    ByteArrayInputStream in = new ByteArrayInputStream(testInput.getBytes());
    System.setIn(in);

    ViewImpl view = new ViewImpl();
    int[] result = view.welcomeMessage();

    assertArrayEquals(new int[] {8, 8}, result);
    assertEquals("""
        Hello! Welcome to the OOD BattleSalvo Game!
        Please enter a valid height and width below:
        ------------------------------------------------------
        """, outputStream.toString());
  }

  /**
   * Test case for the welcomeMessage method with invalid input followed by valid input.
   * Asserts that the returned array and the printed error message match the expected values.
   */
  @Test
  public void testWelcomeMessageInvalidInputThenValidInput() {
    String testInput = "5 6\n8 8\n";
    ByteArrayInputStream in = new ByteArrayInputStream(testInput.getBytes());
    System.setIn(in);

    ViewImpl view = new ViewImpl();
    int[] result = view.welcomeMessage();

    assertArrayEquals(new int[] {8, 8}, result);
    assertEquals("""
        Hello! Welcome to the OOD BattleSalvo Game!
        Please enter a valid height and width below:
        ------------------------------------------------------
        Uh Oh! You've entered invalid dimensions. Please remember that the height and width
        of the game must be in the range [6, 15], inclusive. Try again!
        ------------------------------------------------------
        """, outputStream.toString());
  }

  /**
   * Test case for the welcomeMessage method with invalid input followed by valid input.
   * Asserts that the returned array and the printed error message match the expected values.
   */
  @Test
  public void testWelcomeMessageInvalidInputThenValidInput2() {
    String testInput = "17 8\n8 8\n";
    ByteArrayInputStream in = new ByteArrayInputStream(testInput.getBytes());
    System.setIn(in);

    ViewImpl view = new ViewImpl();
    int[] result = view.welcomeMessage();

    assertArrayEquals(new int[] {8, 8}, result);
    assertEquals("""
        Hello! Welcome to the OOD BattleSalvo Game!
        Please enter a valid height and width below:
        ------------------------------------------------------
        Uh Oh! You've entered invalid dimensions. Please remember that the height and width
        of the game must be in the range [6, 15], inclusive. Try again!
        ------------------------------------------------------
        """, outputStream.toString());
  }

  /**
   * Test case for the welcomeMessage method with invalid input followed by valid input.
   * Asserts that the returned array and the printed error message match the expected values.
   */
  @Test
  public void testWelcomeMessageInvalidInputThenValidInput3() {
    String testInput = "15 17\n8 8\n";
    ByteArrayInputStream in = new ByteArrayInputStream(testInput.getBytes());
    System.setIn(in);

    ViewImpl view = new ViewImpl();
    int[] result = view.welcomeMessage();

    assertArrayEquals(new int[] {8, 8}, result);
    assertEquals("""
        Hello! Welcome to the OOD BattleSalvo Game!
        Please enter a valid height and width below:
        ------------------------------------------------------
        Uh Oh! You've entered invalid dimensions. Please remember that the height and width
        of the game must be in the range [6, 15], inclusive. Try again!
        ------------------------------------------------------
        """, outputStream.toString());
  }

  /**
   * Test case for the welcomeMessage method with invalid input followed by valid input.
   * Asserts that the returned array and the printed error message match the expected values.
   */
  @Test
  public void testWelcomeMessageInvalidInputThenValidInput4() {
    String testInput = "6 5\n8 8\n";
    ByteArrayInputStream in = new ByteArrayInputStream(testInput.getBytes());
    System.setIn(in);

    ViewImpl view = new ViewImpl();
    int[] result = view.welcomeMessage();

    assertArrayEquals(new int[] {8, 8}, result);
    assertEquals("""
        Hello! Welcome to the OOD BattleSalvo Game!
        Please enter a valid height and width below:
        ------------------------------------------------------
        Uh Oh! You've entered invalid dimensions. Please remember that the height and width
        of the game must be in the range [6, 15], inclusive. Try again!
        ------------------------------------------------------
        """, outputStream.toString());
  }

  /**
   * Test case for testing valid input for getShips method
   */
  @Test
  public void testGetShipsValidInput() {
    String testInput = "3 2 2 1\n";
    ByteArrayInputStream in = new ByteArrayInputStream(testInput.getBytes());
    System.setIn(in);

    ViewImpl view = new ViewImpl();
    Map<ShipType, Integer> result = view.getShips(8);

    assertEquals(3, result.get(ShipType.CARRIER).intValue());
    assertEquals(2, result.get(ShipType.BATTLESHIP).intValue());
    assertEquals(2, result.get(ShipType.DESTROYER).intValue());
    assertEquals(1, result.get(ShipType.SUBMARINE).intValue());
    assertEquals(
        """
            Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].
            Remember, your fleet may not exceed size 8.
            --------------------------------------------------------------------------------
            """,
        outputStream.toString());
  }

  /**
   * Test case for testing invalid input then valid input for getShips method
   */
  @Test
  public void testGetShipsInvalidInputThenValidInput() {
    String testInput = "2 1 1 1\n3 2 2 1\n";
    ByteArrayInputStream in = new ByteArrayInputStream(testInput.getBytes());
    System.setIn(in);

    ViewImpl view = new ViewImpl();
    Map<ShipType, Integer> result = view.getShips(8);

    assertEquals(3, result.get(ShipType.CARRIER).intValue());
    assertEquals(2, result.get(ShipType.BATTLESHIP).intValue());
    assertEquals(2, result.get(ShipType.DESTROYER).intValue());
    assertEquals(1, result.get(ShipType.SUBMARINE).intValue());
    assertEquals(
        """
            Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].
            Remember, your fleet may not exceed size 8.
            --------------------------------------------------------------------------------
            Uh Oh! You've entered invalid fleet sizes.
            Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].
            Remember, your fleet may not exceed size 8.
            --------------------------------------------------------------------------------
            """,
        outputStream.toString());
  }

  /**
   * Test case for testing invalid input then valid input for getShips method
   */
  @Test
  public void testGetShipsInvalidInputThenValidInput2() {
    String testInput = "2 1 1 0\n3 2 2 1\n";
    ByteArrayInputStream in = new ByteArrayInputStream(testInput.getBytes());
    System.setIn(in);

    ViewImpl view = new ViewImpl();
    Map<ShipType, Integer> result = view.getShips(8);

    assertEquals(3, result.get(ShipType.CARRIER).intValue());
    assertEquals(2, result.get(ShipType.BATTLESHIP).intValue());
    assertEquals(2, result.get(ShipType.DESTROYER).intValue());
    assertEquals(1, result.get(ShipType.SUBMARINE).intValue());
    assertEquals(
        """
            Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].
            Remember, your fleet may not exceed size 8.
            --------------------------------------------------------------------------------
            Uh Oh! You've entered invalid fleet sizes.
            Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].
            Remember, your fleet may not exceed size 8.
            --------------------------------------------------------------------------------
            """,
        outputStream.toString());
  }

  //  /**
  //   * Test case for the showAIBoard method with a valid board.
  //   * Asserts that the printed board matches the expected value.
  //   */
  //  @Test
  //  public void testShowAiBoard() {
  //    char[][] array = new char[4][4];
  //    for (char[] chars : array) {
  //      Arrays.fill(chars, '0');
  //    }
  //    ViewImpl view = new ViewImpl();
  //    view.showAIBoard(array);
  //    assertEquals(
  //        """
  //            Opponent Board Data:
  //            0 0 0 0\s
  //            0 0 0 0\s
  //            0 0 0 0\s
  //            0 0 0 0\s
  //             """,
  //        outputStream.toString());
  //  }

  /**
   * Test case for the showPlayerBoard method with a valid board.
   * Asserts that the printed board matches the expected value.
   */
  @Test
  public void testShowPlayerBoard() {
    char[][] array = new char[4][4];
    for (char[] chars : array) {
      Arrays.fill(chars, '0');
    }
    ViewImpl view = new ViewImpl();
    view.showHumanBoard(array);
    assertEquals(
        """
            Your Board:
            0 0 0 0\s
            0 0 0 0\s
            0 0 0 0\s
            0 0 0 0\s
             """,
        outputStream.toString());
  }

  /**
   * Test to make sure correct response for entering shots
   */
  @Test
  public void testEnterShots() {
    String testInput = "2 1\n";
    ByteArrayInputStream in = new ByteArrayInputStream(testInput.getBytes());
    System.setIn(in);

    ViewImpl view = new ViewImpl();
    List<Coord> shots = view.enterShots(1);
    assertEquals(shots.get(0).x(), 1);
    assertEquals(shots.get(0).y(), 2);
    assertEquals(
        """
            Please Enter 1 Shots:
            ------------------------------------------------------------------
             """,
        outputStream.toString());
  }

  /**
   * Test to make sure correct response for player winning the game
   */
  @Test
  public void testGameOverWin() {
    ViewImpl view = new ViewImpl();
    view.gameOver(GameResult.WIN);
    assertEquals(
        """
            Congratulations, you won the game!
             """,
        outputStream.toString());
  }

  /**
   * Test to make sure correct response for player losing the game
   */
  @Test
  public void testGameOverLose() {
    ViewImpl view = new ViewImpl();
    view.gameOver(GameResult.LOSE);
    assertEquals(
        """
            Unfortunately, you lost the game.
             """,
        outputStream.toString());
  }

  /**
   * Test to make sure correct response for a game end in a tie
   */
  @Test
  public void testGameOverTie() {
    ViewImpl view = new ViewImpl();
    view.gameOver(GameResult.TIE);
    assertEquals(
        """
            You tied!
             """,
        outputStream.toString());
  }

}
