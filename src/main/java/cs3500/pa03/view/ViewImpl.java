package cs3500.pa03.view;

import cs3500.pa03.model.Coord;
import cs3500.pa03.model.GameResult;
import cs3500.pa03.model.ShipType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * The ViewImpl class implements the View interface and provides the user interface for the game.
 */
public class ViewImpl implements View {
  private final Scanner scanner;

  /**
   * Constructs a ViewImpl object.
   * Initializes the scanner to read user input from the console.
   */
  public ViewImpl() {
    this.scanner = new Scanner(System.in);
  }

  @Override
  public int[] welcomeMessage() {
    System.out.println("""
        Hello! Welcome to the OOD BattleSalvo Game!
        Please enter a valid height and width below:
        ------------------------------------------------------""");
    int[] boardSize = new int[2];
    boardSize[0] = getNextInt();
    boardSize[1] = getNextInt();

    return validateBoardSize(boardSize);
  }

  private int getNextInt() {
    return scanner.nextInt();
  }

  private void printInvalidBoardSizeMessage() {
    System.out.println("""
        Uh Oh! You've entered invalid dimensions. Please remember that the height and width
        of the game must be in the range [6, 15], inclusive. Try again!
        ------------------------------------------------------""");
    scanner.nextLine(); // Consume the newline character
  }

  private int[] validateBoardSize(int[] boardSize) {
    while (boardSize[0] < 6 || boardSize[0] > 15 || boardSize[1] < 6 || boardSize[1] > 15) {
      printInvalidBoardSizeMessage();
      boardSize[0] = getNextInt();
      boardSize[1] = getNextInt();
    }
    return boardSize;
  }

  @Override
  public Map<ShipType, Integer> getShips(int maxFleetSize) {
    int[] fleetShips = new int[4];
    System.out.printf("""
        Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].
        Remember, your fleet may not exceed size %d.
        --------------------------------------------------------------------------------
        """, maxFleetSize);
    fleetShips[0] = getNextInt();
    fleetShips[1] = getNextInt();
    fleetShips[2] = getNextInt();
    fleetShips[3] = getNextInt();
    return validateFleetSize(fleetShips, maxFleetSize);
  }

  private void printInvalidFleetSizeMessage(int maxFleetSize) {
    System.out.printf("""
        Uh Oh! You've entered invalid fleet sizes.
        Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].
        Remember, your fleet may not exceed size %d.
        --------------------------------------------------------------------------------
        """, maxFleetSize);
    scanner.nextLine(); // Consume the newline character
  }

  private Map<ShipType, Integer> validateFleetSize(int[] inputFleetSize, int maxFleetSize) {
    Map<ShipType, Integer> outputShips = new HashMap<>();
    boolean correctInput = false;
    while (!correctInput) {
      int shipSum = 0;
      for (int shipNum : inputFleetSize) {
        shipSum += shipNum;
      }

      boolean foundShipsZero = false;
      for (int shipNum : inputFleetSize) {
        if (shipNum == 0) {
          foundShipsZero = true;
          break;
        }
      }

      if (shipSum != maxFleetSize || foundShipsZero) {
        printInvalidFleetSizeMessage(maxFleetSize);
        inputFleetSize[0] = getNextInt();
        inputFleetSize[1] = getNextInt();
        inputFleetSize[2] = getNextInt();
        inputFleetSize[3] = getNextInt();
      } else {
        correctInput = true;
      }
    }
    outputShips.put(ShipType.CARRIER, inputFleetSize[0]);
    outputShips.put(ShipType.BATTLESHIP, inputFleetSize[1]);
    outputShips.put(ShipType.DESTROYER, inputFleetSize[2]);
    outputShips.put(ShipType.SUBMARINE, inputFleetSize[3]);
    return outputShips;
  }

  @Override
  public void showAiBoard(char[][] initialArray) {
    System.out.println("Opponent Board Data:");
    for (char[] chars : initialArray) {
      for (char charA : chars) {
        System.out.print(charA + " ");
      }
      System.out.println();
    }
  }

  @Override
  public void showHumanBoard(char[][] initialArray) {
    System.out.println("Your Board:");
    for (char[] chars : initialArray) {
      for (char charA : chars) {
        System.out.print(charA + " ");
      }
      System.out.println();
    }
  }

  @Override
  public List<Coord> enterShots(int maxShots) {
    List<Coord> coordList = new ArrayList<>();
    System.out.printf("""
        Please Enter %d Shots:
        ------------------------------------------------------------------
        """, maxShots);
    for (int i = 0; i < maxShots; i++) {
      int x = scanner.nextInt();
      int y = scanner.nextInt();
      Coord coord = new Coord(y, x);
      coordList.add(coord);
    }
    return coordList;
  }

  @Override
  public void gameOver(GameResult gameResult) {
    if (gameResult.equals(GameResult.WIN)) {
      System.out.println("Congratulations, you won the game!");
    } else if (gameResult.equals(GameResult.LOSE)) {
      System.out.println("Unfortunately, you lost the game.");
    } else {
      System.out.println("You tied!");
    }
  }
}
