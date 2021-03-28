package com.marshmallow.hiring.instructions.parsers;

import com.marshmallow.hiring.instructions.exception.InvalidArgumentException;
import com.marshmallow.hiring.instructions.model.Position;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.NonNull;
import org.springframework.stereotype.Component;

/**
 * Spring Component that provides the functionality to parse a list of objects into the internal
 * representation of a position.
 * <p>
 * If the provided format is not the one currently expected, {@link InvalidArgumentException} is
 * thrown.
 */
@Component
public class PositionParser {

  /**
   * Parses a list of objects, as provided by the user, into the internal representation of a
   * position.
   *
   * @param coords list of coordinates, as provided by the user
   * @return the representation of the position as a {@link Position} object
   * @throws InvalidArgumentException If the provided position format is invalid
   */
  public Position parse(@NonNull List<Object> coords) throws InvalidArgumentException {
    if (coords.size() != 2) {
      throw new InvalidArgumentException("Position did not contain exactly 2 coordinates");
    }

    int x = parseCoordinate(coords.get(0));
    int y = parseCoordinate(coords.get(1));

    return new Position(x, y);
  }

  /**
   * Parses an object supposer to contain  one of the two coordinates of a position
   *
   * @param obj the generic object containing the coordinate
   * @return the integer representation of the coordinate
   * @throws InvalidArgumentException If the provided object is null, not an Integer, or has
   *                                  negative value
   */
  public int parseCoordinate(Object obj) throws InvalidArgumentException {
    if (!(obj instanceof Integer)) {
      throw new InvalidArgumentException("Coordinate '" + obj + "' is not an integer");
    }
    int coord = (Integer) obj;

    if (coord < 0) {
      throw new InvalidArgumentException("Coordinate '" + coord + "' is not allowed");
    }

    return coord;
  }

  /**
   * Utility method that parses a set of positions using the {@link PositionParser#parse(List)}
   * method.
   *
   * @param positionsRaw set of positions as provided by the user
   * @return a set of {@link Position} as returned by the {@link PositionParser#parse(List)} method
   * @throws InvalidArgumentException If any of the provided positions format is invalid
   */
  public Set<Position> parseMultiple(@NonNull Set<List<Object>> positionsRaw)
      throws InvalidArgumentException {
    Set<Position> result = new HashSet<>();
    for (List<Object> coords : positionsRaw) {
      result.add(parse(coords));
    }
    return result;
  }
}
