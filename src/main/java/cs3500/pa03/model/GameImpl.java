package cs3500.pa03.model;

import cs3500.pa03.view.View;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The GameImpl class represents an implementation of the Game interface.
 * It provides functionality for setting up the game board and running the game loop.
 */
public class GameImpl implements Game {
  private final PlayerImpl manualPlayer;
  private final PlayerImpl aiPlayer;
  private Board playerBoard;
  private Board aiBoard;

  /**
   * Constructs a GameImpl object.
   * Initializes the manual player and AI player.
   */
  public GameImpl() {
    this.manualPlayer = new ManualPlayer();
    this.aiPlayer = new AiPlayer();
  }

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
  @Override
  public List<char[][]> setUpBoard(int boardHeight, int boardWidth,
                                   Map<ShipType, Integer> shipsNums) {
    List<Ship> manualShips = manualPlayer.setup(boardHeight, boardWidth, shipsNums);
    List<Ship> aiShips = aiPlayer.setup(boardHeight, boardWidth, shipsNums);
    playerBoard = new PlayerBoard(boardHeight, boardWidth, manualShips);
    manualPlayer.setBoard(playerBoard);
    aiBoard = new AiBoard(boardHeight, boardWidth, aiShips);
    aiPlayer.setBoard(aiBoard);
    aiPlayer.setPlayerBoard(playerBoard);
    List<char[][]> boardSetup = new ArrayList<>();
    boardSetup.add(playerBoard.returnBoard());
    boardSetup.add(aiBoard.returnBoard());
    return boardSetup;
  }

  /**
   * Runs the game loop.
   * This method is responsible for the main game logic and interactions with the view.
   *
   * @param view The view to interact with during the game loop.
   * @return The result of the game.
   */

  @Override
  public GameResult gameLoop(View view) {
    while (playerBoard.getShipNum() != 0 && aiBoard.getShipNum() != 0) {
      List<Coord> shotsEntered = view.enterShots(playerBoard.getShipNum());
      List<Coord> validatedShots = validateShots(view, shotsEntered);
      playerBoard.setCurrentShots(validatedShots);
      aiBoard.updateShots(manualPlayer.takeShots());

      List<Coord> aiShots = aiPlayer.takeShots();
      playerBoard.updateShots(aiShots);

      view.showAiBoard(aiBoard.returnBoard());
      view.showHumanBoard(playerBoard.returnBoard());
    }
    if (playerBoard.getShipNum() == 0) {
      return GameResult.LOSE;
    } else if (aiBoard.getShipNum() == 0) {
      return GameResult.WIN;
    } else {
      return GameResult.TIE;
    }
  }

  private List<Coord> validateShots(View view, List<Coord> coordList) {
    boolean isValidShot = false;
    List<Coord> outputCoordList = coordList;
    while (!isValidShot) {
      for (Coord coord : outputCoordList) {
        isValidShot = coord.x() < playerBoard.width && coord.x() >= 0 && coord.y() >= 0
            && coord.y() < playerBoard.height;
        if (!isValidShot) {
          break;
        }
        boolean isFound = false;
        for (Coord remainingCoord : aiBoard.remainingShots) {
          if (remainingCoord.x() == coord.x() && remainingCoord.y() == coord.y()) {
            isFound = true;
            break;
          }
        }
        if (!isFound) {
          isValidShot = false;
        }
      }
      if (!isValidShot) {
        System.out.println("Try again!");
        outputCoordList = view.enterShots(coordList.size());
      }
    }
    return outputCoordList;
  }
}