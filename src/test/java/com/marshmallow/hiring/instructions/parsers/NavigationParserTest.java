package com.marshmallow.hiring.instructions.parsers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.marshmallow.hiring.instructions.exception.InvalidArgumentException;
import com.marshmallow.hiring.instructions.model.NavigationType;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NavigationParserTest {

  private NavigationParser underTest;

  @BeforeEach
  void setUp() {
    underTest = new NavigationParser();
  }

  @Test
  void deserialize_ValidInput() throws Exception {
    // Arrange
    String input = "NWSE";
    List<NavigationType> expected = List.of(
        NavigationType.GO_NORTH,
        NavigationType.GO_WEST,
        NavigationType.GO_SOUTH,
        NavigationType.GO_EAST
    );

    // Act
    List<NavigationType> actual = underTest.parse(input);

    // Assert
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void deserialize_InvalidInput() {
    // Arrange
    String input = "NWHSE";

    // Act and Assert
    assertThrows(InvalidArgumentException.class, () -> underTest.parse(input));
  }

  @Test
  void deserialize_EmptyInput() throws Exception {
    // Arrange
    String input = "";
    List<NavigationType> expected = List.of();

    // Act
    List<NavigationType> actual = underTest.parse(input);

    // Assert
    assertThat(actual).isEqualTo(expected);
  }
}