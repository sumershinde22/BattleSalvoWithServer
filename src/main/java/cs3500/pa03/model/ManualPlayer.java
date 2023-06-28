package cs3500.pa03.model;

import static cs3500.pa03.model.AiPlayer.getDamageCoords;

import java.util.List;

/**
 * manual player class that allows for a user to play game through console
 */
public class ManualPlayer extends PlayerImpl {

  @Override
  public String name() {
    return null;
  }

  @Override
  public List<Coord> takeShots() {
    return this.board.currentShots;
  }

  @Override
  public List<Coord> reportDamage(List<Coord> opponentShotsOnBoard) {
    return getDamageCoords(opponentShotsOnBoard, this.board);
  }

  @Override
  public void successfulHits(List<Coord> shotsThatHitOpponentShips) {

  }

  @Override
  public void endGame(GameResult result, String reason) {
  }

  @Override
  public List<Coord> takeShotsFromServer() {
    return null;
  }

  @Override
  public void setPlayerBoard(Board playerBoard) {}
}
