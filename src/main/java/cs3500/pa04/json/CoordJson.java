package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * json coord representation
 *
 * @param x coordinate for column
 * @param y coordinate for row
 */
public record CoordJson(
    @JsonProperty("x") int x,
    @JsonProperty("y") int y) {
}
