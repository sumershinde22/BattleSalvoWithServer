package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa04.Direction;

/**
 * json ship representation
 *
 * @param coord first coordinate of a json
 * @param length of ship i.e. how many coordinates it contains
 * @param direction ship faces: horizontal or vertical
 */
public record ShipJson(
    @JsonProperty("coord") CoordJson coord,
    @JsonProperty("length") int length,
    @JsonProperty("direction") Direction direction) {
}
