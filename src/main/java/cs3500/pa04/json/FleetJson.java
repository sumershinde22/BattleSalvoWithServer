package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * json representation of a fleet
 *
 * @param ships containing first coord, length, and direction
 */
public record FleetJson(
    @JsonProperty("fleet") List<ShipJson> ships) {
}
