package com.marshmallow.hiring.instructions.deserializers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.marshmallow.hiring.instructions.model.NavigationType;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NavigationDeserializerTest {

  private ObjectMapper objectMapper;

  @BeforeEach
  void setUp() {
    objectMapper = new ObjectMapper();
  }

  @Test
  void deserialize_ValidInput() throws Exception {
    // Arrange
    String json = "{\"navigationInstructions\": \"NWSE\"}";
    List<NavigationType> cmds = List.of(
        NavigationType.GO_NORTH,
        NavigationType.GO_WEST,
        NavigationType.GO_SOUTH,
        NavigationType.GO_EAST
    );
    NavContainer expected = new NavContainer(cmds);

    // Act
    NavContainer actual = objectMapper.readValue(json, NavContainer.class);

    // Assert
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void deserialize_InvalidInput() {
    // Arrange
    String json = "{\"navigationInstructions\": \"NWHSE\"}";

    // Act and Assert
    assertThrows(JsonMappingException.class,
        () -> objectMapper.readValue(json, NavContainer.class));
  }

  @Test
  void deserialize_EmptyInput() throws Exception {
    // Arrange
    String json = "{\"navigationInstructions\": \"\"}";
    List<NavigationType> cmds = List.of();
    NavContainer expected = new NavContainer(cmds);

    // Act
    NavContainer actual = objectMapper.readValue(json, NavContainer.class);

    // Assert
    assertThat(actual).isEqualTo(expected);
  }

  @NoArgsConstructor
  @AllArgsConstructor
  @EqualsAndHashCode
  private static class NavContainer {

    @JsonDeserialize(using = NavigationDeserializer.class)
    private List<NavigationType> navigationInstructions;
  }
}