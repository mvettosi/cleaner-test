package com.marshmallow.hiring.instructions.model;

import java.util.List;
import java.util.Set;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 * Data transfer object used as request body of the instructions endpoint.
 */
@Data
public class InstructionsRequest {

  /**
   * The size and shape of the area to operate on. It is represented by to top-right-most allowed
   * position.
   */
  @NotNull
  @Size(min = 2, max = 2)
  private List<Object> areaSize;

  /**
   * The starting position of the cleaner.
   */
  @NotNull
  @Size(min = 2, max = 2)
  private List<Object> startingPosition;

  /**
   * Positions of the oil patches present in the provided area. If any of these patches are outside
   * the boundaries of the provided area, they will be ignored as they are not preventing the
   * calculation of how many will be cleaned by the navigation operation.
   * <p>
   * Since two patches cannot be in the same spot, the set collection is used to ensure that
   * duplicates are removed.
   * <p>
   * Note: adding extra functionality, like calculating the percentage of cleaned patches, might
   * require the addition of validation of their position.
   */

  @NotNull
  private Set<@NotNull @Size(min = 2, max = 2) List<Object>> oilPatches;

  /**
   * A string representing a list of navigation instructions to use when calculating the final state
   * of the cleaner and the sea area.
   */
  @NotNull
  private String navigationInstructions;
}
