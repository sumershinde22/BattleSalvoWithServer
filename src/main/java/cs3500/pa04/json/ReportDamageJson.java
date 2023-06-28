package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa03.model.Coord;
import java.util.List;

/**
 * Json representation of report damage
 *
 * @param coordinates list of coordinates that have been hit on player's board
 */
public record ReportDamageJson(
    @JsonProperty("coordinates") List<Coord> coordinates) {
}
