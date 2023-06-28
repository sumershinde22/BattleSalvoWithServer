package cs3500.pa03.model;

/**
 * The ShipType enum represents different types of ships in the game.
 * Each ship type has a character form and a size.
 */
public enum ShipType {
  /**
   * ship with its associated size and marker
   */
  CARRIER('C', 6),
  /**
   * ship with its associated size and marker
   */
  BATTLESHIP('B', 5),
  /**
   * ship with its associated size and marker
   */
  DESTROYER('D', 4),
  /**
   * ship with its associated size and marker
   */
  SUBMARINE('S', 3);


  private final char charForm;
  private final int size;

  /**
   * Constructs a ShipType with the specified character form and size.
   *
   * @param charForm the character form representing the ship type
   * @param size     the size of the ship
   */
  ShipType(char charForm, int size) {
    this.charForm = charForm;
    this.size = size;
  }

  /**
   * Returns the character form representing the ship type.
   *
   * @return the character form of the ship type
   */
  public char getCharForm() {
    return charForm;
  }

  /**
   * Returns the size of the ship.
   *
   * @return the size of the ship
   */
  public int getSize() {
    return size;
  }
}
