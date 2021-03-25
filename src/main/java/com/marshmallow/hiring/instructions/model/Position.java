package com.marshmallow.hiring.instructions.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.marshmallow.hiring.instructions.deserializers.PositionDeserializer;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Simple class representing a point in the space of the fictional sea as modeled for this
 * exercise.
 */
@Data
@AllArgsConstructor
@JsonDeserialize(using = PositionDeserializer.class)
public class Position {
  public int x;
  public int y;
}
