package cs3500.pa03.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class for the ControllerImpl class. It tests the behavior of the run method in different
 * game scenarios.
 */
class ControllerImplTest {
  private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
  private final PrintStream originalOut = System.out;
  private final InputStream originalIn = System.in;

  /**
   * Sets up the streams to capture the console output.
   */
  @BeforeEach
  public void setUpStreams() {
    System.setOut(new PrintStream(outputStream));
  }

  /**
   * Restores the original System.out and System.in streams.
   */
  @AfterEach
  public void restoreStreams() {
    System.setOut(originalOut);
    System.setIn(originalIn);
  }

  /**
   * Test case for the run method.
   * It prepares the input for the Scanner and sets it as the System.in stream.
   * Then it creates an instance of the ControllerImpl class and invokes the run method.
   * Finally, it asserts that the expected output is contained in the captured output stream.
   */
  @Test
  public void testRun() {
    // Prepare input for the Scanner
    String testInput = """
        6 6
        2 2 1 1
        0 0
        0 1
        0 2
        0 3
        0 4
        0 5
        0 0
        0 1
        0 2
        0 3
        0 4
        0 5
        -1 0
        1 1
        1 2
        1 3
        1 4
        1 5
        1 0
        1 1
        1 17
        1 3
        1 4
        1 5
        17 0
        1 1
        1 2
        1 3
        1 4
        1 5
        1 0
        1 1
        1 2
        1 3
        1 4
        1 5
        2 0
        2 1
        2 2
        2 3
        2 4
        2 5
        3 0
        3 1
        3 2
        3 3
        3 4
        3 5
        4 0
        4 1
        4 2
        4 3
        4 4
        4 5
        5 0
        5 1
        5 2
        5 3
        5 4
        5 5
        """;

    ByteArrayInputStream in = new ByteArrayInputStream(testInput.getBytes());
    System.setIn(in);

    Controller controller = new ControllerImpl();
    controller.run();
    String expectedOutput = """
        Hello! Welcome to the OOD BattleSalvo Game!
        Please enter a valid height and width below:
        ------------------------------------------------------
        Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].
        Remember, your fleet may not exceed size 6.
        --------------------------------------------------------------------------------""";
    String winOutput = """
        Congratulations, you won the game!
         """;
    String loseOutput = """
        Unfortunately, you lost the game.
         """;
    String tieOutput = """
        You tied!
         """;
    String actualOutputStream = outputStream.toString();
    assertTrue(actualOutputStream.contains(expectedOutput)
        && (actualOutputStream.contains(winOutput) || actualOutputStream.contains(loseOutput)
        || actualOutputStream.contains(tieOutput)));
  }

}