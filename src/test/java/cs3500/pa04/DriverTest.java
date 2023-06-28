package cs3500.pa04;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * testing that driver errors and correct input
 */
class DriverTest {

  /**
   * testing command line argument for server play throws no error
   */
  @Test
  public void testCorrectServerInput() {
    String[] validArg = new String[] {"0.0.0.0", "35001"};
    assertDoesNotThrow(() -> Driver.main(validArg));
  }

  /**
   * testing for IOException occurring
   */
  @Test
  public void testIoException() {
    String[] invalidArg = new String[] {"invalid", "invalid"};
    assertThrows(IllegalArgumentException.class, () -> Driver.main(invalidArg),
        "IOException encountered");
  }

  /**
   * testing for exception occurring for invalid arguments
   */
  @Test
  public void testInvalidInput() {
    String[] invalidArg = new String[] {"invalid", "invalid"};
    assertThrows(NumberFormatException.class, () -> Driver.main(invalidArg),
        "IOException encountered");
  }

  /**
   * testing if error thrown for >3 cmnd line args
   */
  @Test
  public void testIllegalArgException() {
    String[] invalidArg = new String[] {"invalid", "invalid", "invalid"};
    assertThrows(IllegalArgumentException.class, () -> Driver.main(invalidArg),
        "Please enter valid host and port (2 command line arguments)"
            + " or no command line arguments to play manually");
  }
}