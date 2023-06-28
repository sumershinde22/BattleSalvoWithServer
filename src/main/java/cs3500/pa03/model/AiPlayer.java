package cs3500.pa03.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The AiPlayer class represents an AI player in the game.
 * It extends the PlayerImpl class and provides AI-specific behavior.
 */
public class AiPlayer extends PlayerImpl {
  Board playerBoard;

  @Override
  public String name() {
    return "Ai";
  }

  public void setPlayerBoard(Board playerBoard) {
    this.playerBoard = playerBoard;
  }

  @Override
  public List<Coord> takeShots() {
    Random random = new Random();
    List<Coord> selectedShots = new ArrayList<>();
    int originalSize = playerBoard.remainingShots.size();
    for (int i = 0; i < playerBoard.getShipNum(); i++) {
      int randomIndex = random.nextInt(originalSize);
      selectedShots.add(playerBoard.remainingShots.get(randomIndex));
      playerBoard.remainingShots.remove(randomIndex);
      originalSize--;
    }
    return selectedShots;
  }

  @Override
  public List<Coord> takeShotsFromServer() {
    Random random = new Random();
    List<Coord> selectedShots = new ArrayList<>();
    int originalSize = board.remainingShots.size();
    for (int i = 0; i < board.ships.size(); i++) {
      if (originalSize == 0) {
        break;
      }
      int randomIndex = random.nextInt(originalSize);
      selectedShots.add(board.remainingShots.get(randomIndex));
      board.remainingShots.remove(randomIndex);
      originalSize--;
    }
    return selectedShots;
  }


  @Override
  public List<Coord> reportDamage(List<Coord> opponentShotsOnBoard) {
    return getDamageCoords(opponentShotsOnBoard, board);
  }

  /**
   * Player shots that hit AI board
   *
   * @param opponentShotsOnBoard list of coordinates opponent shot
   * @param aiBoard              representation of AI board
   * @return a list of shots the user hit on AI board
   */
  public static List<Coord> getDamageCoords(List<Coord> opponentShotsOnBoard, Board aiBoard) {
    List<Coord> outputDamages = new ArrayList<>();
    for (Coord shot : opponentShotsOnBoard) {
      for (Ship ship : aiBoard.ships) {
        for (Coord occupied : ship.occupied()) {
          if (shot.x() == occupied.x() && shot.y() == occupied.y()) {
            outputDamages.add(shot);
          }
        }
      }
    }
    return outputDamages;
  }

  @Override
  public void successfulHits(List<Coord> shotsThatHitOpponentShips) {

  }

  @Override
  public void endGame(GameResult result, String reason) {

  }
}