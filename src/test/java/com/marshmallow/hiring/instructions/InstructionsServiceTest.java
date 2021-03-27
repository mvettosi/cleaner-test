package com.marshmallow.hiring.instructions;

import static org.junit.jupiter.api.Assertions.*;

import com.marshmallow.hiring.instructions.exception.InvalidMovementException;
import com.marshmallow.hiring.instructions.model.Instructions;
import com.marshmallow.hiring.instructions.model.Position;
import java.util.List;
import java.util.Set;
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