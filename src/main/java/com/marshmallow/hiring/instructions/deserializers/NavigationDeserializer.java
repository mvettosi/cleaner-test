package com.marshmallow.hiring.instructions.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.marshmallow.hiring.instructions.exception.InvalidArgumentException;
import com.marshmallow.hiring.instructions.model.NavigationType;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Custom Json deserializer to abstract the format in which the list of navigation commands are
 * provided in the http api from the internal business logic that uses them to calculate the result.
 * Changing the format, or adding different alternative ones, will only require this class to be
 * updated.
 * <p>
 * If the provided format is not the one currently expected, {@link InvalidArgumentException} is
 * thrown.
 */
public class NavigationDeserializer extends JsonDeserializer<List<NavigationType>> {
  private static final Map<Character, NavigationType> CMD_MAPPING = Map.of(
      'N', NavigationType.GO_NORTH,
      'S', NavigationType.GO_SOUTH,
      'E', NavigationType.GO_EAST,
      'W', NavigationType.GO_WEST
  );

  @Override
  public List<NavigationType> deserialize(JsonParser jsonParser,
      DeserializationContext deserializationContext) throws IOException {
    JsonNode node = jsonParser.getCodec().readTree(jsonParser);

    if (!node.isTextual()) {
      throw new InvalidArgumentException(
          "List of navigation instructions was not provided as a string");
    }

    String instructions = node.asText();
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
