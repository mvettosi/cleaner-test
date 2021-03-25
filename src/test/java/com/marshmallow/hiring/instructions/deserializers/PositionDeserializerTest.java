package com.marshmallow.hiring.instructions.deserializers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.marshmallow.hiring.instructions.model.Position;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PositionDeserializerTest {

  private ObjectMapper objectMapper;

  @BeforeEach
  void setUp() {
    objectMapper = new ObjectMapper();
  }

  @Test
  void deserialize_ValidInput() throws Exception {
    // Arrange
    String json = "{\"position\": [1, 2]}";
    PositionContainer expected = new PositionContainer(new Position(1, 2));

    // Act
    PositionContainer actual = objectMapper.readValue(json, PositionContainer.class);

    // Assert
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void deserialize_MoreThanTwoCoords() {
    // Arrange
    String json = "{\"position\": [1, 2, 3]}";

    // Act and Assert
    assertThrows(JsonMappingException.class,
        () -> objectMapper.readValue(json, PositionContainer.class));
  }

  @Test
  void deserialize_LessThanTwoCoords() {
    // Arrange
    String json = "{\"position\": [1]}";

    // Act and Assert
    assertThrows(JsonMappingException.class,
        () -> objectMapper.readValue(json, PositionContainer.class));
  }

  @Test
  void deserialize_EmtpyList() {
    // Arrange
    String json = "{\"position\": []}";

    // Act and Assert
    assertThrows(JsonMappingException.class,
        () -> objectMapper.readValue(json, PositionContainer.class));
  }

  @Test
  void deserialize_NonIntCoord() {
    // Arrange
    String json = "{\"position\": [1, \"a\"]}";

    // Act and Assert
    assertThrows(JsonMappingException.class,
        () -> objectMapper.readValue(json, PositionContainer.class));
  }

  @Test
  void deserialize_NonList() {
    // Arrange
    String json = "{\"position\": \"a\"}";

    // Act and Assert
    assertThrows(JsonMappingException.class,
        () -> objectMapper.readValue(json, PositionContainer.class));
  }

  @NoArgsConstructor
  @AllArgsConstructor
  @EqualsAndHashCode
  private static class PositionContainer {

    @JsonDeserialize(using = PositionDeserializer.class)
    private Position position;
  }
}