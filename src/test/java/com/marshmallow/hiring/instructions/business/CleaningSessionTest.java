package com.marshmallow.hiring.instructions.business;

import static com.marshmallow.hiring.instructions.model.NavigationType.GO_EAST;
import static com.marshmallow.hiring.instructions.model.NavigationType.GO_NORTH;
import static com.marshmallow.hiring.instructions.model.NavigationType.GO_SOUTH;
import static com.marshmallow.hiring.instructions.model.NavigationType.GO_WEST;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.marshmallow.hiring.instructions.exception.InvalidArgumentException;
import com.marshmallow.hiring.instructions.exception.InvalidMovementException;
import com.marshmallow.hiring.instructions.model.NavigationType;
import com.marshmallow.hiring.instructions.model.Position;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;

class CleaningSessionTest {

  @Test
  void constructor_StartNoPatch() {
    // Arrange
    Position area = new Position(5, 5);
    Position startingPosition = new Position(1, 2);
    Set<Position> patches = Set.of(new Position(3, 3), new Position(4, 4));

    // Act
    CleaningSession actual = new CleaningSession(area, startingPosition, patches);

    // Assert
    assertThat(actual.getAreaSize()).isEqualTo(area);
    assertThat(actual.getCleanerPosition()).isEqualTo(startingPosition);
    assertThat(actual.getRemainingOilPatches()).isEqualTo(patches);
  }

  @Test
  void constructor_StartOnPatch() {
    // Arrange
    Position area = new Position(5, 5);
    Position startingPosition = new Position(3, 3);
    Set<Position> patches = Set.of(new Position(3, 3), new Position(4, 4));

    // Act
    CleaningSession actual = new CleaningSession(area, startingPosition, patches);

    // Assert
    assertThat(actual.getAreaSize()).isEqualTo(area);
    assertThat(actual.getCleanerPosition()).isEqualTo(startingPosition);
    assertThat(actual.getRemainingOilPatches()).isEqualTo(Set.of(new Position(4, 4)));
  }

  @Test
  void constructor_StartOutOfXBoundsPositive() {
    // Arrange
    Position area = new Position(5, 5);
    Position startingPosition = new Position(6, 3);
    Set<Position> patches = Set.of(new Position(3, 3), new Position(4, 4));

    // Act
    // Assert
    assertThrows(InvalidArgumentException.class,
        () -> new CleaningSession(area, startingPosition, patches));
  }

  @Test
  void constructor_StartOutOfXBoundsNegative() {
    // Arrange
    Position area = new Position(5, 5);
    Position startingPosition = new Position(-3, 3);
    Set<Position> patches = Set.of(new Position(3, 3), new Position(4, 4));

    // Act
    // Assert
    assertThrows(InvalidArgumentException.class,
        () -> new CleaningSession(area, startingPosition, patches));
  }

  @Test
  void constructor_StartOutOfYBoundsPositive() {
    // Arrange
    Position area = new Position(5, 5);
    Position startingPosition = new Position(3, 6);
    Set<Position> patches = Set.of(new Position(3, 3), new Position(4, 4));

    // Act
    // Assert
    assertThrows(InvalidArgumentException.class,
        () -> new CleaningSession(area, startingPosition, patches));
  }

  @Test
  void constructor_StartOutOfYBoundsNegative() {
    // Arrange
    Position area = new Position(5, 5);
    Position startingPosition = new Position(3, -2);
    Set<Position> patches = Set.of(new Position(3, 3), new Position(4, 4));

    // Act
    // Assert
    assertThrows(InvalidArgumentException.class,
        () -> new CleaningSession(area, startingPosition, patches));
  }

  @Test
  void constructor_NoPatches() {
    // Arrange
    Position area = new Position(5, 5);
    Position startingPosition = new Position(1, 2);

    // Act
    CleaningSession actual = new CleaningSession(area, startingPosition, Set.of());

    // Assert
    assertThat(actual.getAreaSize()).isEqualTo(area);
    assertThat(actual.getCleanerPosition()).isEqualTo(startingPosition);
    assertThat(actual.getRemainingOilPatches()).isEmpty();
  }

  @Test
  void constructor_NullArea() {
    // Arrange
    Position startingPosition = new Position(6, 3);
    Set<Position> patches = Set.of(new Position(3, 3), new Position(4, 4));

    // Act
    // Assert
    //noinspection ConstantConditions
    assertThrows(NullPointerException.class,
        () -> new CleaningSession(null, startingPosition, patches));
  }

  @Test
  void constructor_NullStartingPosition() {
    // Arrange
    Position area = new Position(5, 5);
    Set<Position> patches = Set.of(new Position(3, 3), new Position(4, 4));

    // Act
    // Assert
    //noinspection ConstantConditions
    assertThrows(NullPointerException.class,
        () -> new CleaningSession(area, null, patches));
  }

  @Test
  void constructor_NullPatches() {
    // Arrange
    Position area = new Position(5, 5);
    Position startingPosition = new Position(6, 3);

    // Act
    // Assert
    //noinspection ConstantConditions
    assertThrows(NullPointerException.class,
        () -> new CleaningSession(area, startingPosition, null));
  }

  @Test
  void applyInstructions_NullInstructions() {
    // Arrange
    Position area = new Position(5, 5);
    Position startingPosition = new Position(3, 3);
    Set<Position> patches = Set.of(new Position(3, 3), new Position(4, 4));
    CleaningSession actual = new CleaningSession(area, startingPosition, patches);

    // Act
    // Assert
    //noinspection ConstantConditions
    assertThrows(NullPointerException.class,
        () -> actual.applyInstructions(null));
  }

  @Test
  void applyInstructions_EmptyInstructions() throws InvalidMovementException {
    // Arrange
    Position area = new Position(5, 5);
    Position startingPosition = new Position(1, 2);
    Set<Position> patches = Set.of(new Position(3, 3), new Position(4, 4));
    CleaningSession actual = new CleaningSession(area, startingPosition, patches);

    // Act
    actual.applyInstructions(List.of());

    // Assert
    assertThat(actual.getAreaSize()).isEqualTo(area);
    assertThat(actual.getCleanerPosition()).isEqualTo(startingPosition);
    assertThat(actual.getRemainingOilPatches()).isEqualTo(patches);
  }

  @Test
  void applyInstructions_XOutOfBoundsInstructions() {
    // Arrange
    Position area = new Position(5, 5);
    Position startingPosition = new Position(1, 2);
    Set<Position> patches = Set.of(new Position(3, 3), new Position(4, 4));
    CleaningSession actual = new CleaningSession(area, startingPosition, patches);
    List<NavigationType> cmds = List.of(GO_WEST, GO_WEST, GO_WEST);

    // Act
    // Assert
    assertThrows(InvalidMovementException.class, () -> actual.applyInstructions(cmds));
  }

  @Test
  void applyInstructions_YOutOfBoundsInstructions() {
    // Arrange
    Position area = new Position(5, 5);
    Position startingPosition = new Position(1, 2);
    Set<Position> patches = Set.of(new Position(3, 3), new Position(4, 4));
    CleaningSession actual = new CleaningSession(area, startingPosition, patches);
    List<NavigationType> cmds = List.of(GO_SOUTH, GO_SOUTH, GO_SOUTH, GO_SOUTH);

    // Act
    // Assert
    assertThrows(InvalidMovementException.class, () -> actual.applyInstructions(cmds));
  }

  @Test
  void applyInstructions_NonCleaningInstructions() throws InvalidMovementException {
    // Arrange
    Position area = new Position(5, 5);
    Position startingPosition = new Position(1, 2);
    Set<Position> patches = Set.of(new Position(3, 3), new Position(4, 4));
    CleaningSession actual = new CleaningSession(area, startingPosition, patches);
    List<NavigationType> cmds = List.of(GO_NORTH, GO_WEST, GO_SOUTH, GO_SOUTH);

    // Act
    actual.applyInstructions(cmds);

    // Assert
    assertThat(actual.getAreaSize()).isEqualTo(area);
    assertThat(actual.getCleanerPosition()).isEqualTo(new Position(0, 1));
    assertThat(actual.getRemainingOilPatches()).isEqualTo(patches);
  }

  @Test
  void applyInstructions_CleaningInstructions() throws InvalidMovementException {
    // Arrange
    Position area = new Position(5, 5);
    Position startingPosition = new Position(1, 2);
    Set<Position> patches = Set.of(new Position(3, 3), new Position(4, 4));
    CleaningSession actual = new CleaningSession(area, startingPosition, patches);
    List<NavigationType> cmds = List.of(GO_NORTH, GO_EAST, GO_EAST, GO_SOUTH);

    // Act
    actual.applyInstructions(cmds);

    // Assert
    assertThat(actual.getAreaSize()).isEqualTo(area);
    assertThat(actual.getCleanerPosition()).isEqualTo(new Position(3, 2));
    assertThat(actual.getRemainingOilPatches()).isEqualTo(Set.of(new Position(4, 4)));
  }
}