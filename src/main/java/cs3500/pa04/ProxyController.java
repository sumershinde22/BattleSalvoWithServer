package cs3500.pa04;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa03.model.AiBoard;
import cs3500.pa03.model.Board;
import cs3500.pa03.model.Coord;
import cs3500.pa03.model.PlayerImpl;
import cs3500.pa03.model.Ship;
import cs3500.pa04.json.CoordJson;
import cs3500.pa04.json.EndGameJson;
import cs3500.pa04.json.FleetJson;
import cs3500.pa04.json.JoinJson;
import cs3500.pa04.json.JsonUtils;
import cs3500.pa04.json.MessageJson;
import cs3500.pa04.json.ReportDamageJson;
import cs3500.pa04.json.SetupJson;
import cs3500.pa04.json.ShipJson;
import cs3500.pa04.json.TakeShotsJson;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Proxy Pattern is used by this class to talk to the Server and dispatch methods to the Player
 */
public class ProxyController {
  private final Socket server;
  private final InputStream in;
  private final PrintStream out;
  private final PlayerImpl player;
  private final ObjectMapper mapper = new ObjectMapper();

  /**
   * Construct an instance of a ProxyPlayer.
   *
   * @param server the socket connection to the server
   * @param player the instance of the player
   * @throws IOException if io error occurs
   */
  public ProxyController(Socket server, PlayerImpl player) throws IOException {
    this.server = server;
    this.in = server.getInputStream();
    this.out = new PrintStream(server.getOutputStream());
    this.player = player;
  }

  /**
   * Listens for messages from the server as JSON in the format of a MessageJSON. When a complete
   * message is sent by the server, the message is parsed and then delegated to the corresponding
   * helper method for each message. This method stops when the connection to the server is closed
   * or an IOException is thrown from parsing malformed JSON.
   */
  public void run() {
    try {
      JsonParser parser = this.mapper.getFactory().createParser(this.in);

      while (!this.server.isClosed()) {
        MessageJson message = parser.readValueAs(MessageJson.class);
        delegateMessage(message);
      }
    } catch (IOException e) {
      // Disconnected from server or parsing exception
    }
  }

  /**
   * Determines the type of request the server has sent and delegates to the
   * corresponding helper method with the message arguments.
   *
   * @param message the MessageJSON used to determine what the server has sent
   */
  private void delegateMessage(MessageJson message) {
    String methodName = message.methodName();
    JsonNode arguments = message.arguments();

    if ("join".equals(methodName)) {
      handleJoin();
    } else if ("setup".equals(methodName)) {
      handleSetup(arguments);
    } else if ("take-shots".equals(methodName)) {
      handleTakeShots();
    } else if ("report-damage".equals(methodName)) {
      handleReportDamage(arguments);
    } else if ("successful-hits".equals(methodName)) {
      handleSuccessfulHits();
    } else if ("end-game".equals(methodName)) {
      handleEndGame(arguments);
    } else {
      throw new IllegalStateException("Invalid message name");
    }
  }

  /**
   * handles sending name and type of game given server input
   */
  public void handleJoin() {
    JoinJson arguments = new JoinJson("pa04-teamsushi", GameType.SINGLE);
    JsonNode joinArguments = JsonUtils.serializeRecord(arguments);
    MessageJson response = new MessageJson("join", joinArguments);
    JsonNode jsonResponse = JsonUtils.serializeRecord(response);
    this.out.println(jsonResponse);
  }

  /**
   * handles taking server input and client response for the
   * setup method
   *
   * @param arguments of setupJSON
   */
  public void handleSetup(JsonNode arguments) {
    SetupJson setupArguments = this.mapper.convertValue(arguments, SetupJson.class);
    // obtain a list of ships while calling setup method
    List<Ship> shipList = player.setup(setupArguments.height(), setupArguments.width(),
        setupArguments.fleetSpec());

    // add correct elements to a shipAdapter list to match desired client response
    List<ShipJson> shipJsons = new ArrayList<>();
    for (Ship ship : shipList) {

      Coord firstCoord = ship.occupied().get(0);
      int length = ship.occupied().size();
      Direction direction = ship.getDirection();
      CoordJson coord = new CoordJson(firstCoord.x(), firstCoord.y());

      shipJsons.add(new ShipJson(coord, length, direction));
    }
    Board board = new AiBoard(setupArguments.height(), setupArguments.width(), shipList);
    player.setBoard(board);
    // serialize response & send to server
    FleetJson response = new FleetJson(shipJsons);
    JsonNode jsonResponse = JsonUtils.serializeRecord(response);
    MessageJson msg = new MessageJson("setup", jsonResponse);
    JsonNode msgSent = JsonUtils.serializeRecord(msg);
    this.out.println(msgSent);
  }

  /**
   * Parses the given arguments as a TakeShotJson, serializes players shots and sends response to
   * the server.
   */
  public void handleTakeShots() {
    List<Coord> shots = player.takeShotsFromServer();
    TakeShotsJson arguments = new TakeShotsJson(shots);
    JsonNode takeShotsArguments = JsonUtils.serializeRecord(arguments);
    MessageJson response = new MessageJson("take-shots", takeShotsArguments);
    JsonNode jsonResponse = JsonUtils.serializeRecord(response);
    this.out.println(jsonResponse);
  }

  /**
   * Parses the given arguments as a ReportDamageJSON, serializes player's damage and
   * sends response to server
   *
   * @param arguments report damage Json representation
   */
  public void handleReportDamage(JsonNode arguments) {
    ReportDamageJson reportDamageArguments =
        this.mapper.convertValue(arguments, ReportDamageJson.class);
    List<Coord> inputShots = reportDamageArguments.coordinates();
    List<Coord> shotsHit = player.reportDamage(inputShots);
    player.board.updateShotsFromServer(inputShots);
    ReportDamageJson reportDamageJson = new ReportDamageJson(shotsHit);
    JsonNode reportDamageArgs = JsonUtils.serializeRecord(reportDamageJson);
    MessageJson response = new MessageJson("report-damage", reportDamageArgs);
    JsonNode jsonResponse = JsonUtils.serializeRecord(response);
    this.out.println(jsonResponse);
  }

  /**
   * Parses the given arguments as a SuccesfulHitsJSON, serializes player's hits and
   * sends response to server
   */
  public void handleSuccessfulHits() {
    MessageJson response = new MessageJson("successful-hits", mapper.createObjectNode());
    JsonNode jsonResponse = JsonUtils.serializeRecord(response);
    this.out.println(jsonResponse);
  }

  /**
   * Parses given arguments as EndGame json and sends void response
   *
   * @param arguments EndGame json representation
   */
  public void handleEndGame(JsonNode arguments) {
    try {
      EndGameJson endGameJson = this.mapper.convertValue(arguments, EndGameJson.class);
      this.player.endGame(endGameJson.result(), endGameJson.reason());
      MessageJson response = new MessageJson("end-game", mapper.createObjectNode());
      JsonNode jsonResponse = JsonUtils.serializeRecord(response);
      this.out.println(jsonResponse);
      server.close();
    } catch (IOException e) {
      System.out.println("IOException occurred");
    }
  }
}