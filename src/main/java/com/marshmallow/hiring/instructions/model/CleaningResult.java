package com.marshmallow.hiring.instructions.model;

import lombok.Builder;
import lombok.Data;

/**
 * Data transfer object designed to represent the result of the cleaning operations.
 */
@Data
@Builder
public class CleaningResult {

  /**
   * The final position of the cleaner.
   */
  Position finalPosition;

  /**
   * The amount of oil patches that were cleaned during the cleaning operations.
   */
  int oilPatchesCleaned;
}
