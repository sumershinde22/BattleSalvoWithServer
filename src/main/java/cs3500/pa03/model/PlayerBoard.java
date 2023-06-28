package cs3500.pa03.model;

import java.util.Arrays;
import java.util.List;

/**
 * The PlayerBoard class represents the game board of a player.
 * It extends the abstract class Board and provides functionality specific to the human player's
 * board.
 */
public class PlayerBoard extends Board {
  /**
   * Constructs a PlayerBoard object with the specified height, width, and list of ships.
   *
   * @param height The height of the player board.
   * @param width  The width of the player board.
   * @param ships  The list of ships placed on the player board.
   */
  public PlayerBoard(int height, int width, List<Ship> ships) {
    super(height, width, ships);
  }

  /**
   * Returns the 2D char array representation of the player board.
   * The board includes the ships' positions and the shots hit.
   *
   * @return The 2D char array representing the player board.
   */
  @Override
  public char[][] returnBoard() {
    char[][] newBoard = new char[height][width];
    for (char[] chars : newBoard) {
      // Assign '0' to each element
      Arrays.fill(chars, '0');
    }
    for (Ship ship : ships) {
      List<Coord> occupiedCoords = ship.occupied();
      for (Coord coord : occupiedCoords) {
        for (int i = 0; i < newBoard.length; i++) {
          for (int j = 0; j < newBoard[i].length; j++) {
            if (coord.x() == i && coord.y() == j) {
              newBoard[i][j] = ship.type().getCharForm();
            }
          }
        }
      }
    }

    for (int i = 0; i < newBoard.length; i++) {
      for (int j = 0; j < newBoard[i].length; j++) {
        for (Coord hitShots : shotsHit) {
          if (hitShots.x() == i && hitShots.y() == j) {
            newBoard[i][j] = 'H';
            break;
          }
        }
      }
    }
    return newBoard;
  }

  /**
   * Updates the player board based on the shots fired.
   * The method removes hit ships from the board and adds the shots to the list of shots hit.
   *
   * @param shots The list of shots fired by the opponent.
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
    }
  }

  @Override
  public void updateShotsFromServer(List<Coord> inputShots) {

  }
}