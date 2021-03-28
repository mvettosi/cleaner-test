package com.marshmallow.hiring.instructions.parsers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.marshmallow.hiring.instructions.exception.InvalidArgumentException;
import com.marshmallow.hiring.instructions.model.Position;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PositionParserTest {

  private PositionParser underTest;

  @BeforeEach
  void setUp() {
    underTest = new PositionParser();
  }

  @Test
  void parse_ValidInput() throws Exception {
    // Arrange
    List<Object> input = List.of(1, 2);
    Position expected = new Position(1, 2);

    // Act
    Position actual = underTest.parse(input);

    // Assert
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void parse_MoreThanTwoCoords() {
    // Arrange
    List<Object> input = List.of(1, 2, 3);

    // Act
    // Assert
    assertThrows(InvalidArgumentException.class, () -> underTest.parse(input));
  }

  @Test
  void parse_LessThanTwoCoords() {
    // Arrange
    List<Object> input = List.of(1);

    // Act
    // Assert
    assertThrows(InvalidArgumentException.class, () -> underTest.parse(input));
  }

  @Test
  void parse_EmtpyList() {
    // Arrange
    List<Object> input = List.of();

    // Act
    // Assert
    assertThrows(InvalidArgumentException.class, () -> underTest.parse(input));
  }

  @Test
  void parse_NonIntCoord() {
    // Arrange
    // Act
    // Assert
    assertThrows(InvalidArgumentException.class, () -> underTest.parseCoordinate("a"));
  }

  @Test
  void parse_NullCoord() {
    // Arrange
    // Act
    // Assert
    assertThrows(InvalidArgumentException.class, () -> underTest.parseCoordinate(null));
  }

  @Test
  void parse_NegativeCoord() {
    // Arrange
    // Act
    // Assert
    assertThrows(InvalidArgumentException.class, () -> underTest.parseCoordinate(-1));
  }

  @Test
  void parse_ValidCoord() throws InvalidArgumentException {
    // Arrange
    int expected = 1;

    // Act
    int actual = underTest.parseCoordinate(expected);

    // Assert
    assertThat(actual).isEqualTo(expected);
  }
}