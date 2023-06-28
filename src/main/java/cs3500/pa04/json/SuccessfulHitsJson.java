package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa03.model.Coord;
import java.util.List;

/**
 * successful hits json representation
 *
 * @param coords that hit opponent board
 */
public record SuccessfulHitsJson(
    @JsonProperty("coordinates") List<Coord> coords) {
}
