package com.marshmallow.hiring.instructions.parsers;

import com.marshmallow.hiring.instructions.exception.InvalidArgumentException;
import com.marshmallow.hiring.instructions.model.NavigationType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.NonNull;
import org.springframework.stereotype.Component;

/**
 * Spring Component that provides the functionality to parse string into an internal representation
 * of the navigation instructions for a cleaner.
 * <p>
 * If the provided format is not the one currently expected, {@link InvalidArgumentException} is
 * thrown.
 */
@Component
public class NavigationParser {

  private static final Map<Character, NavigationType> CMD_MAPPING = Map.of(
      'N', NavigationType.GO_NORTH,
      'S', NavigationType.GO_SOUTH,
      'E', NavigationType.GO_EAST,
      'W', NavigationType.GO_WEST
  );

  /**
   * Parses a description of the instructions, as provided by the user, into an internal
   * representation of the navigation instructions for a cleaner.
   *
   * @param instructions the string representing the instructions to be parsed. Must be not null
   * @return A list of {@link NavigationType} matching the provided instruction string
   * @throws InvalidArgumentException if the provided instruction string is invalid
   */
  public List<NavigationType> parse(@NonNull String instructions) throws InvalidArgumentException {
    List<NavigationType> result = new ArrayList<>();

    for (char code : instructions.toCharArray()) {
      if (!CMD_MAPPING.containsKey(code)) {
        throw new InvalidArgumentException("Code '" + code + "' is not a valid instruction type");
      }
      result.add(CMD_MAPPING.get(code));
    }

    return result;
  }
}
