package cs3500.pa04;

import cs3500.pa03.controller.Controller;
import cs3500.pa03.controller.ControllerImpl;
import cs3500.pa03.model.AiPlayer;
import cs3500.pa03.model.PlayerImpl;
import java.io.IOException;
import java.net.Socket;

/**
 * This is the main driver of this project.
 */
public class Driver {
  /**
   * This method connects to the server at the given host and port, builds a proxy referee
   * to handle communication with the server, and sets up a client player.
   *
   * @param host the server host
   * @param port the server port
   */
  private static void runClient(String host, int port) {
    try {
      Socket server = new Socket(host, port);
      PlayerImpl control = new AiPlayer();
      ProxyController pc = new ProxyController(server, control);
      pc.run();
    } catch (IOException i) {
      System.out.println("IOException occurred");
    } catch (IllegalStateException e) {
      System.out.println("Illegal state");
    }
  }

  /**
   * Project entry point
   *
   * @param args - no command line args required
   */
  public static void main(String[] args) {
    // local CPU vs. server CPU if 2 command line arguments are given
    if (args.length == 2) {
      String host = args[0];
      int port = Integer.parseInt(args[1]);
      Driver.runClient(host, port);
    } else if (args.length == 0) {
      // human cpu if no command line arguments are given
      Controller controller = new ControllerImpl();
      controller.run();
    } else {
      throw new IllegalArgumentException("Please enter valid host and port "
          + "(2 command line arguments)"
          + " or no command line arguments to play manually");
    }
  }
}