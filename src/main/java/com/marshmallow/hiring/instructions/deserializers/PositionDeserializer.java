package com.marshmallow.hiring.instructions.deserializers;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.marshmallow.hiring.instructions.exception.InvalidArgumentException;
import com.marshmallow.hiring.instructions.model.Position;
import java.io.IOException;

/**
 * Custom Json deserializer to abstract the format in which a position in space is provided in the
 * http api from the internal business logic that uses them to calculate the result. Changing the
 * format, or adding different alternative ones, will only require this class to be updated.
 * <p>
 * If the provided format is not the one currently expected, {@link InvalidArgumentException} is
 * thrown.
 */
public class PositionDeserializer extends JsonDeserializer<Position> {

  @Override
  public Position deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
      throws IOException {
    JsonNode node = jsonParser.getCodec().readTree(jsonParser);

    // Validation
    if (!node.isArray()) {
      throw new InvalidArgumentException("Position was not provided as an array");
    }
    if (node.size() != 2) {
      throw new InvalidArgumentException("Position did not contain exactly 2 coordinates");
    }
    for (JsonNode coord : node) {
      if (!coord.isInt()) {
        throw new JsonParseException(jsonParser,
            "Coordinate '" + coord.asText() + "' is not an integer");
      }
    }

    int x = node.get(0).asInt();
    int y = node.get(1).asInt();
    return new Position(x, y);
  }
}
