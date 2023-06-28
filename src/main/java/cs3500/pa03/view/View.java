package cs3500.pa03.view;

import cs3500.pa03.model.Coord;
import cs3500.pa03.model.GameResult;
import cs3500.pa03.model.ShipType;
import java.util.List;
import java.util.Map;

/**
 * The View interface represents the user interface for the game.
 * It defines methods for displaying messages, getting user input, and updating the game state.
 */
public interface View {
  /**
   * Displays the welcome message and returns the dimensions of the game board.
   *
   * @return an array containing the height and width of the game board
   */
  int[] welcomeMessage();

  /**
   * Prompts the user to enter the number of ships for each ship type.
   * Returns a map containing the ship types and their corresponding counts.
   *
   * @param maxFleetSize the maximum number of ships allowed in the fleet
   * @return a map of ship types and their counts
   */
  Map<ShipType, Integer> getShips(int maxFleetSize);

  /**
   * Displays the AI board with the initial state.
   *
   * @param initialArray the initial state of the AI board as a 2D character array
   */
  void showAiBoard(char[][] initialArray);

  /**
   * Displays the human player's board with the initial state.
   *
   * @param initialArray the initial state of the human player's board as a 2D character array
   */
  void showHumanBoard(char[][] initialArray);

  /**
   * Prompts the user to enter a list of shots to take on the opponent's board.
   * Returns the list of coordinates representing the shots.
   *
   * @param maxShots the maximum number of shots allowed
   * @return a list of coordinates representing the shots
   */
  List<Coord> enterShots(int maxShots);

  /**
   * Displays the game over message with the result of the game.
   *
   * @param gameResult the result of the game (WIN, LOSE, or TIE)
   */
  void gameOver(GameResult gameResult);
}
