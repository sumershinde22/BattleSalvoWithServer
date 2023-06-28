package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa04.GameType;

/**
 * json format for join
 *
 * @param githubUsername players github username
 * @param gameType type of game they want to play (either single or multi)
 */
public record JoinJson(
    @JsonProperty("name") String githubUsername,
    @JsonProperty("game-type") GameType gameType) {
}
