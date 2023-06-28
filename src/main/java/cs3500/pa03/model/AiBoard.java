package cs3500.pa03.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The AiBoard class represents the game board for the AI player.
 * It extends the Board class and adds functionality specific to the AI player.
 */
public class AiBoard extends Board {

  private final List<Coord> shotsMissed;

  /**
   * Constructs an AiBoard object with the specified height, width, and list of ships.
   *
   * @param height The height of the board.
   * @param width  The width of the board.
   * @param ships  The list of ships on the board.
   */
  public AiBoard(int height, int width, List<Ship> ships) {
    super(height, width, ships);
    shotsMissed = new ArrayList<>();
  }

  /**
   * Returns the current state of the board as a 2D char array.
   * '0' represents empty cells, 'H' represents hit cells, and 'M' represents missed cells.
   *
   * @return The board represented as a 2D char array.
   */
  @Override
  public char[][] returnBoard() {
    char[][] newBoard = new char[height][width];
    for (char[] chars : newBoard) {
      // Assign '0' to each element
      Arrays.fill(chars, '0');
    }

    for (int i = 0; i < newBoard.length; i++) {
      for (int j = 0; j < newBoard[i].length; j++) {
        for (Coord hitShots : shotsHit) {
          if (hitShots.x() == i && hitShots.y() == j) { // Swap i and j
            newBoard[i][j] = 'H'; // Swap i and j
            break;
          }
        }
        for (Coord missedShot : shotsMissed) {
          if (missedShot.x() == i && missedShot.y() == j) { // Swap i and j
            newBoard[i][j] = 'M'; // Swap i and j
            break;
          }
        }
      }
    }
    return newBoard;
  }

  /**
   * Updates the board with the specified list of shots.
   * It marks the hit or missed cells accordingly and updates the state of the ships.
   *
   * @param shots The list of shots to update on the board.
   */
  public void updateShots(List<Coord> shots) {
    for (Coord shot : shots) {
      remainingShots.remove(shot);
      boolean hit = false;
      for (Ship ship : ships) {
        for (Coord occupied : ship.occupied()) {
          if (shot.x() == occupied.x() && shot.y() == occupied.y()) {
            shotsHit.add(shot);
            hit = true;
            ship.occupied().remove(occupied);
            break;
          }
        }
        if (ship.occupied().isEmpty()) {
          ships.remove(ship);
        }
        if (hit) {
          break;
        }
      }
      if (!hit) {
        shotsMissed.add(shot);
      }
    }
  }

  /**
   * updates shots being sent
   *
   * @param shots from server
   */
  public void updateShotsFromServer(List<Coord> shots) {
    for (Coord shot : shots) {
      boolean hit = false;
      for (Ship ship : ships) {
        for (Coord occupied : ship.occupied()) {
          if (shot.x() == occupied.x() && shot.y() == occupied.y()) {
            hit = true;
            ship.occupied().remove(occupied);
            break;
          }
        }
        if (ship.occupied().isEmpty()) {
          ships.remove(ship);
        }
        if (hit) {
          break;
        }
      }
    }
  }
}