package com.marshmallow.hiring.instructions.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.marshmallow.hiring.instructions.deserializers.PositionDeserializer;
import com.marshmallow.hiring.instructions.exception.InvalidMovementException;
import javax.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

/**
 * Simple class representing a point in the space of the fictional sea as modeled for this
 * exercise.
 */
@Data
@AllArgsConstructor
@JsonDeserialize(using = PositionDeserializer.class)
public class Position {

  @Min(0)
  public int x;
  @Min(0)
  public int y;

  /**
   * Moves the current position according to the supplied instruction.
   *
   * @param instruction The type of movement to be performed.
   * @throws InvalidMovementException If the supplied type of movement is not supported yet.
   */
  public void move(@NonNull NavigationType instruction) throws InvalidMovementException {
    switch (instruction) {
      case GO_NORTH:
        y++;
        break;
      case GO_SOUTH:
        y--;
        break;
      case GO_EAST:
        x++;
        break;
      case GO_WEST:
        x--;
        break;
      default:
        throw new InvalidMovementException(
            "The supplied navigation instruction is not supported yet");
    }
  }
}
