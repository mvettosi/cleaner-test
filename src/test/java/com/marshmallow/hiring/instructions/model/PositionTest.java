package com.marshmallow.hiring.instructions.model;

import static com.marshmallow.hiring.instructions.model.NavigationType.GO_EAST;
import static com.marshmallow.hiring.instructions.model.NavigationType.GO_NORTH;
import static com.marshmallow.hiring.instructions.model.NavigationType.GO_SOUTH;
import static com.marshmallow.hiring.instructions.model.NavigationType.GO_WEST;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.marshmallow.hiring.instructions.exception.InvalidMovementException;
import org.junit.jupiter.api.Test;

class PositionTest {

  @Test
  void move_East() throws InvalidMovementException {
    // Arrange
    Position position = new Position(2, 2);

    // Act
    position.move(GO_EAST);

    // Assert
    assertThat(position.getX()).isEqualTo(3);
    assertThat(position.getY()).isEqualTo(2);
  }

  @Test
  void move_West() throws InvalidMovementException {
    // Arrange
    Position position = new Position(2, 2);

    // Act
    position.move(GO_WEST);

    // Assert
    assertThat(position.getX()).isEqualTo(1);
    assertThat(position.getY()).isEqualTo(2);
  }

  @Test
  void move_North() throws InvalidMovementException {
    // Arrange
    Position position = new Position(2, 2);

    // Act
    position.move(GO_NORTH);

    // Assert
    assertThat(position.getX()).isEqualTo(2);
    assertThat(position.getY()).isEqualTo(3);
  }

  @Test
  void move_South() throws InvalidMovementException {
    // Arrange
    Position position = new Position(2, 2);

    // Act
    position.move(GO_SOUTH);

    // Assert
    assertThat(position.getX()).isEqualTo(2);
    assertThat(position.getY()).isEqualTo(1);
  }

  @Test
  void move_NullDirection() {
    // Arrange
    Position position = new Position(2, 2);

    // Act
    // Assert
    //noinspection ConstantConditions
    assertThrows(NullPointerException.class, () -> position.move(null));
  }
}