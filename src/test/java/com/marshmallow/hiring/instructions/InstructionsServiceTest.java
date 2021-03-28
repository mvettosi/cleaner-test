package com.marshmallow.hiring.instructions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.marshmallow.hiring.instructions.model.CleaningResult;
import com.marshmallow.hiring.instructions.model.InstructionsRequest;
import com.marshmallow.hiring.instructions.parsers.NavigationParser;
import com.marshmallow.hiring.instructions.parsers.PositionParser;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InstructionsServiceTest {

  private InstructionsService underTest;

  @BeforeEach
  void setUp() {
    underTest = new InstructionsService(new NavigationParser(), new PositionParser());
  }

  @Test
  void calculateFinalState_NullInstructions() {
    // Arrange
    // Act
    // Assert
    assertThrows(NullPointerException.class, () -> underTest.calculateFinalState(null));
  }

  @Test
  void calculateFinalState_InstructionsWithNulls() {
    // Arrange
    InstructionsRequest instructions = new InstructionsRequest();

    // Act
    // Assert
    assertThrows(NullPointerException.class, () -> underTest.calculateFinalState(instructions));
  }

  @Test
  void calculateFinalState_HappyPath() throws Exception {
    // Arrange
    InstructionsRequest instructions = new InstructionsRequest();
    instructions.setAreaSize(List.of(5, 5));
    instructions.setStartingPosition(List.of(1, 2));
    instructions.setOilPatches(Set.of(List.of(1, 0), List.of(2, 2), List.of(2, 3)));
    instructions.setNavigationInstructions("NNESEESWNWW");
    CleaningResult expected = CleaningResult.builder().finalPosition(List.of(1, 3))
        .oilPatchesCleaned(1).build();

    // Act
    CleaningResult actual = underTest.calculateFinalState(instructions);

    // Assert
    assertThat(actual).isEqualTo(expected);
  }
}