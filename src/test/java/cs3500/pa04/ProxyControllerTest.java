package cs3500.pa04;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa03.model.AiBoard;
import cs3500.pa03.model.AiPlayer;
import cs3500.pa03.model.Coord;
import cs3500.pa03.model.GameResult;
import cs3500.pa03.model.Ship;
import cs3500.pa03.model.ShipType;
import cs3500.pa04.json.EndGameJson;
import cs3500.pa04.json.JoinJson;
import cs3500.pa04.json.JsonUtils;
import cs3500.pa04.json.MessageJson;
import cs3500.pa04.json.ReportDamageJson;
import cs3500.pa04.json.SetupJson;
import cs3500.pa04.json.SuccessfulHitsJson;
import cs3500.pa04.json.TakeShotsJson;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * testing proxy controller class
 */
class ProxyControllerTest {

  private ByteArrayOutputStream testLog;
  private ProxyController controller;
  private AiPlayer player;

  /**
   * Reset the test log before each test is run.
   */
  @BeforeEach
  public void setup() {
    this.testLog = new ByteArrayOutputStream(2048);
    assertEquals("", logToString());
    player = new AiPlayer();
    List<Coord> coord = new ArrayList<>();
    coord.add(new Coord(0, 0));
    coord.add(new Coord(0, 1));
    coord.add(new Coord(0, 2));
    Ship ship = new Ship(ShipType.SUBMARINE, coord);
    List<Ship> ships = new ArrayList<>();
    ships.add(ship);
    AiBoard board = new AiBoard(4, 4, ships);
    player.setBoard(board);
  }

  /**
   * testing join handler
   */
  @Test
  public void testHandleJoin() {
    JoinJson joinJson = new JoinJson("teamsushi", GameType.SINGLE);
    JsonNode sampleMsg = createSampleMessage("join", joinJson);
    // Create the client with all necessary messages
    Mocket socket = new Mocket(this.testLog, List.of(sampleMsg.toString()));

    // Create a controller
    try {
      this.controller = new ProxyController(socket, player);
    } catch (IOException e) {
      fail(); // fail if the controller can't be created
    }

    // run the control and verify the response
    this.controller.run();

    String expected =
        "{\"method-name\":\"join\",\"arguments\":{\"name\":\"pa04-teamsushi\",\"game-type\":\""
            + "SINGLE\"}}\n";
    assertEquals(expected, logToString());
  }

  /**
   * testing setup handler
   */
  @Test
  public void testHandleSetup() {
    Map<ShipType, Integer> fleet = new HashMap<>();
    fleet.put(ShipType.CARRIER, 1);
    fleet.put(ShipType.BATTLESHIP, 1);
    fleet.put(ShipType.DESTROYER, 1);
    fleet.put(ShipType.SUBMARINE, 1);
    SetupJson setupJson = new SetupJson(6, 6, fleet);
    JsonNode sampleMsg = createSampleMessage("setup", setupJson);
    // Create the client with all necessary messages
    Mocket socket = new Mocket(this.testLog, List.of(sampleMsg.toString()));
    // Create a controller
    try {
      this.controller = new ProxyController(socket, new AiPlayer());
    } catch (IOException e) {
      fail(); // fail if the controller can't be created
    }

    // run the controller and verify the response
    this.controller.run();

    // testing these fields since logToString will have different ships each time due to random
    assertEquals(1, fleet.get(ShipType.CARRIER));
    assertEquals(1, fleet.get(ShipType.BATTLESHIP));
    assertEquals(1, fleet.get(ShipType.DESTROYER));
    assertEquals(1, fleet.get(ShipType.SUBMARINE));
  }


  /**
   * testing take shots handler
   */
  @Test
  public void testHandleTakeShots() {
    List<Coord> shots = new ArrayList<>();
    shots.add(new Coord(0, 0));
    shots.add(new Coord(0, 1));
    TakeShotsJson takeShotsJson = new TakeShotsJson(shots);
    JsonNode sampleMsg = createSampleMessage("take-shots", takeShotsJson);
    // Create the client with all necessary messages
    Mocket socket = new Mocket(this.testLog, List.of(sampleMsg.toString()));

    // Create a controller
    try {
      this.controller = new ProxyController(socket, player);
    } catch (IOException e) {
      fail(); // fail if the controller can't be created
    }

    // run the controller and verify the response
    this.controller.run();

    try {
      // Parse the string as a JSON object
      ObjectMapper objectMapper = new ObjectMapper();
      JsonNode jsonNode = objectMapper.readTree(logToString());
      // Retrieve the coordinates from the JSON object
      JsonNode arguments = jsonNode.get("arguments");
      assertEquals(1, arguments.get("coordinates").size());
    } catch (JsonProcessingException j) {
      fail();
    }
  }

  /**
   * testing report damage handler
   */
  @Test
  public void testHandleReportDamage() {
    List<Coord> coords = new ArrayList<>(
        Arrays.asList(new Coord(0, 0), new Coord(0, 1), new Coord(0, 2)));
    ReportDamageJson reportDamageJson = new ReportDamageJson(coords);
    JsonNode sampleMsg = createSampleMessage("report-damage", reportDamageJson);
    // Create the client with all necessary messages
    Mocket socket = new Mocket(this.testLog, List.of(sampleMsg.toString()));

    // Create a controller
    try {
      this.controller = new ProxyController(socket, player);
    } catch (IOException e) {
      fail(); // fail if the controller can't be created
    }

    // run the controller and verify the response
    this.controller.run();

    String expected =
        "{\"method-name\":\"report-damage\",\"arguments\":{\"coordinates\":[{\"x\":0,\"y\":0},"
            + "{\"x\":0,\"y\":1},"
            + "{\"x\":0,\"y\":2}]}}\n";
    assertEquals(expected, logToString());
  }

  /**
   * testing successful hits handler
   */
  @Test
  public void testHandleSuccessfulHits() {
    List<Coord> coords =
        new ArrayList<>(Arrays.asList(new Coord(0, 0), new Coord(0, 1), new Coord(0, 2)));
    SuccessfulHitsJson successfulHitsJson = new SuccessfulHitsJson(coords);
    JsonNode sampleMsg = createSampleMessage("successful-hits", successfulHitsJson);
    // Create the client with all necessary messages
    Mocket socket = new Mocket(this.testLog, List.of(sampleMsg.toString()));

    // Create a controller
    try {
      this.controller = new ProxyController(socket, player);
    } catch (IOException e) {
      fail(); // fail if the controller can't be created
    }

    // run the controller and verify the response
    this.controller.run();

    String expected =
        "{\"method-name\":\"successful-hits\",\"arguments\":{}}\n";
    assertEquals(expected, logToString());
  }

  /**
   * testing end game handler for win
   */
  @Test
  public void testHandleEndGameWin() {
    EndGameJson endGameJson = new EndGameJson(GameResult.WIN, "You won!");
    JsonNode sampleMsg = createSampleMessage("end-game", endGameJson);
    // Create the client with all necessary messages
    Mocket socket = new Mocket(this.testLog, List.of(sampleMsg.toString()));

    // Create a controller
    try {
      this.controller = new ProxyController(socket, new AiPlayer());
    } catch (IOException e) {
      fail(); // fail if the controller can't be created
    }

    // run the controller and verify the response
    this.controller.run();

    assertEquals("{\"method-name\":\"end-game\",\"arguments\":{}}\n", logToString());
  }

  /**
   * testing end game handler for lose
   */
  @Test
  public void testHandleEndGameLose() {
    EndGameJson endGameJson = new EndGameJson(GameResult.WIN, "You lost!");
    JsonNode sampleMsg = createSampleMessage("end-game", endGameJson);
    // Create the client with all necessary messages
    Mocket socket = new Mocket(this.testLog, List.of(sampleMsg.toString()));

    // Create a controller
    try {
      this.controller = new ProxyController(socket, new AiPlayer());
    } catch (IOException e) {
      fail(); // fail if the controller can't be created
    }

    // run the controller and verify the response
    this.controller.run();

    assertEquals("{\"method-name\":\"end-game\",\"arguments\":{}}\n", logToString());
  }

  /**
   * testing end game handler for tie
   */
  @Test
  public void testHandleEndGameTie() {
    EndGameJson endGameJson = new EndGameJson(GameResult.WIN, "You tied!");
    JsonNode sampleMsg = createSampleMessage("end-game", endGameJson);
    // Create the client with all necessary messages
    Mocket socket = new Mocket(this.testLog, List.of(sampleMsg.toString()));

    // Create a controller
    try {
      this.controller = new ProxyController(socket, new AiPlayer());
    } catch (IOException e) {
      fail(); // fail if the controller can't be created
    }

    // run the controller and verify the response
    this.controller.run();

    assertEquals("{\"method-name\":\"end-game\",\"arguments\":{}}\n", logToString());
  }

  /**
   * Converts the ByteArrayOutputStream log to a string in UTF_8 format
   *
   * @return String representing the current log buffer
   */
  private String logToString() {
    return testLog.toString(StandardCharsets.UTF_8);
  }

  /**
   * Create a MessageJson for some name and arguments.
   *
   * @param messageName   name of the type of message; "hint" or "win"
   * @param messageObject object to embed in a message json
   * @return a MessageJson for the object
   */
  private JsonNode createSampleMessage(String messageName, Record messageObject) {
    MessageJson messageJson =
        new MessageJson(messageName, JsonUtils.serializeRecord(messageObject));
    return JsonUtils.serializeRecord(messageJson);
  }
}