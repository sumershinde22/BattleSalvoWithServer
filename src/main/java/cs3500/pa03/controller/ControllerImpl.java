package cs3500.pa03.controller;


import cs3500.pa03.model.Game;
import cs3500.pa03.model.GameImpl;
import cs3500.pa03.model.GameResult;
import cs3500.pa03.model.ShipType;
import cs3500.pa03.view.View;
import cs3500.pa03.view.ViewImpl;
import java.util.List;
import java.util.Map;


/**
 * The ControllerImpl class is responsible for controlling the flow of the game and managing the
 * interaction
 * between the model (Game) and the view (View).
 */
public class ControllerImpl implements Controller {
  Game game;
  View view;

  /**
   * Constructs a ControllerImpl object and initializes the game and view.
   * The game is initialized with a GameImpl instance and the view is initialized with a ViewImpl
   * instance.
   */
  public ControllerImpl() {
    game = new GameImpl();
    view = new ViewImpl();
  }

  /**
   * Runs the game by performing the following steps:
   * Retrieves the board size from the view by calling welcomeMessage().
   * Initializes the board height and width based on the retrieved board size.
   * Determines the fleet size as the minimum of board height and board width.
   * Retrieves the number of ships for each ship type from the view by calling getShips(fleetSize).
   * Sets up the game boards by calling setUpBoard(boardHeight, boardWidth, shipsNums) on the game.
   * Shows the AI board and human board on the view by calling showAIBoard() and showHumanBoard()
   * respectively.
   * Enters the game loop by calling gameLoop() on the game and passing the view as a parameter.
   * Retrieves the game result from the game loop.
   * Notifies the view about the game result by calling gameOver() and passing the game result.
   */
  @Override
  public void run() {
    int[] boardSize = view.welcomeMessage();
    int boardHeight = boardSize[0];
    int boardWidth = boardSize[1];
    int fleetSize = Math.min(boardHeight, boardWidth);
    Map<ShipType, Integer> shipsNums = view.getShips(fleetSize);

    List<char[][]> setUpBoards = game.setUpBoard(boardHeight, boardWidth, shipsNums);
    view.showAiBoard(setUpBoards.get(1));
    view.showHumanBoard(setUpBoards.get(0));

    GameResult loopOutput = game.gameLoop(view);
    view.gameOver(loopOutput);
  }
}