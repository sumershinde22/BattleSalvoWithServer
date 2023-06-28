package cs3500.pa03.model;

import cs3500.pa03.view.View;
import java.util.List;
import java.util.Map;

/**
 * The Game interface represents a game.
 * It defines methods for setting up the game board and running the game loop.
 */
public interface Game {
  /**
   * Sets up the game board with the specified board height, board width, and ship numbers.
   *
   * @param boardHeight The height of the game board.
   * @param boardWidth  The width of the game board.
   * @param shipsNums   A map of ship types and their corresponding numbers to be placed on the
   *                    board.
   * @return A list of two 2D char arrays representing the AI player's board and the human player's
   *         board.
   */
  List<char[][]> setUpBoard(int boardHeight, int boardWidth, Map<ShipType, Integer> shipsNums);

  /**
   * Runs the game loop.
   * This method is responsible for the main game logic and interactions with the view.
   *
   * @param view The view to interact with during the game loop.
   * @return The result of the game.
   */
  GameResult gameLoop(View view);
}
