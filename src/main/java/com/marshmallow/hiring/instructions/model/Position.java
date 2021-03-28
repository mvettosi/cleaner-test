package com.marshmallow.hiring.instructions.model;

import com.marshmallow.hiring.instructions.exception.InvalidMovementException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

/**
 * Simple class representing a point in the space of the fictional sea as modeled for this code
 * test.
 */
@Data
@AllArgsConstructor
public class Position {

  public int x;
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
