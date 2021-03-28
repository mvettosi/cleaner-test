package com.marshmallow.hiring.instructions;

import static org.junit.jupiter.api.Assertions.assertThrows;

import com.marshmallow.hiring.instructions.model.Instructions;
import org.junit.jupiter.api.Test;

class InstructionsServiceTest {

  @Test
  void calculateFinalState_InstructionsWithNulls() {
    // Arrange
    InstructionsService underTest = new InstructionsService();
    Instructions instructions = new Instructions();

    // Act
    // Assert
    assertThrows(NullPointerException.class, () -> underTest.calculateFinalState(instructions));
  }
}