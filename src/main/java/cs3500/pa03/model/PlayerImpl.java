package cs3500.pa03.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;


/**
 * The PlayerImpl class is an abstract class that provides common functionality for player
 * implementations.
 * It implements the Player interface and defines methods related to setting up ships,
 * taking shots,
 * reporting damage,
 * and handling game events.
 */
public abstract class PlayerImpl implements Player {
  /**
   * board
   */
  public Board board;

  /**
   * Sets the player boards for the player.
   *
   * @param board The player's own board.
   */
  public void setBoard(Board board) {
    this.board = board;
  }

  @Override
  public abstract String name();

  @Override
  public List<Ship> setup(int height, int width, Map<ShipType, Integer> specifications) {
    List<Ship> ships = new ArrayList<>();
    List<Coord> availableCoords = generateAvailableCoords(height, width);

    List<ShipType> shipTypes = new ArrayList<>(specifications.keySet());
    Collections.shuffle(shipTypes);

    for (ShipType shipType : shipTypes) {
      int count = specifications.get(shipType);

      for (int i = 0; i < count; i++) {
        boolean placed = false;

        while (!placed) {
          if (availableCoords.isEmpty()) {
            break;
          }
          Random random = new Random();
          int randomIndex = random.nextInt(availableCoords.size());
          Coord startCoord = availableCoords.get(randomIndex);

          List<Coord> occupied = createOccupiedCoords(startCoord, shipType.getSize());

          if (areCoordinatesValid(occupied, height, width, ships)) {
            Ship ship = new Ship(shipType, occupied);
            ships.add(ship);
            availableCoords.removeAll(occupied);
            placed = true;
          } else {
            availableCoords.remove(startCoord);
          }
        }
      }
    }

    return ships;
  }

  private boolean areCoordinatesValid(List<Coord> occupied, int height, int width,
                                      List<Ship> ships) {
    for (Coord coord : occupied) {
      int x = coord.x();
      int y = coord.y();

      if (x < 0 || x >= width || y < 0 || y >= height) {
        return false; // Coordinate is out of bounds
      }

      for (Ship ship : ships) {
        if (ship.occupied().contains(coord)) {
          return false; // Coordinate overlaps with an existing ship
        }
      }
    }

    return true; // All coordinates are valid
  }

  private List<Coord> createOccupiedCoords(Coord startCoord, int size) {
    List<Coord> occupied = new ArrayList<>();
    int x = startCoord.x();
    int y = startCoord.y();

    for (int i = 0; i < size; i++) {
      Coord coord = new Coord(x, y);
      occupied.add(coord);

      // Update the coordinate based on the desired placement direction
      // (e.g., increment x for horizontal placement, increment y for vertical placement)

      // Example of horizontal placement
      //x++;

      // Example of vertical placement
      y++;
    }

    return occupied;
  }

  private List<Coord> generateAvailableCoords(int height, int width) {
    List<Coord> availableCoords = new ArrayList<>();

    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        Coord coord = new Coord(x, y);
        availableCoords.add(coord);
      }
    }

    return availableCoords;
  }

  @Override
  public abstract List<Coord> takeShots();


  @Override
  public abstract List<Coord> reportDamage(List<Coord> opponentShotsOnBoard);

  @Override
  public void successfulHits(List<Coord> shotsThatHitOpponentShips) {

  }

  @Override
  public void endGame(GameResult result, String reason) {

  }

  /**
   * obtain list of server shots player takes
   *
   * @return list of shots from server player takes
   */
  public abstract List<Coord> takeShotsFromServer();

  /**
   * set the player's board
   *
   * @param playerBoard board of player
   */
  public abstract void setPlayerBoard(Board playerBoard);
}