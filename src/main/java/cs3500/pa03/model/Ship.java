package cs3500.pa03.model;

import cs3500.pa04.Direction;
import java.util.List;

/**
 * The Ship class represents a ship on the game board.
 * It consists of a ship type and a list of occupied coordinates.
 */
public record Ship(ShipType type, List<Coord> occupied) {
  /**
   * get ships direction
   *
   * @return direction
   */
  public Direction getDirection() {
    int firstCoordX = occupied.get(0).x();

    for (Coord coord : occupied) {
      if (coord.x() != firstCoordX) {
        return Direction.HORIZONTAL;
      }
    }
    return Direction.VERTICAL;
  }
}
