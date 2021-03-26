package com.marshmallow.hiring.instructions.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.marshmallow.hiring.instructions.deserializers.NavigationDeserializer;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * Data transfer object used as request body of the instructions endpoint.
 */
@Data
public class Instructions {

  /**
   * The size and shape of the area to operate on. It is represented by to top-right-most allowed
   * position.
   */
  @Valid
  @NotNull
  private Position areaSize;

  /**
   * The starting position of the cleaner.
   */
  @Valid
  @NotNull
  private Position startingPosition;

  /**
   * List of positions of the oil patches present in the provided area. If any of these patches are
   * outside the boundaries of the provided area, they will be ignored as they are not preventing
   * the calculation of how many will be cleaned by the navigation operation.
   * <p>
   * Note: adding extra functionality, like calculating the percentage of cleaned patches, might
   * require the addition of validation of their position.
   */
  @Valid
  private List<Position> oilPatches;

  /**
   * The list of navigation instructions to use when calculating the final state of the cleaner and
   * the sea area.
   */
  @JsonDeserialize(using = NavigationDeserializer.class)
  private List<NavigationType> navigationInstructions;
}
