package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa03.model.Coord;
import java.util.List;

/**
 * json representation of a volley
 *
 * @param shots client took
 */
public record VolleyJson(
    @JsonProperty("shots") List<Coord> shots) {
}
