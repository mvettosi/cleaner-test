package com.marshmallow.hiring.instructions;

import com.marshmallow.hiring.instructions.business.CleaningSession;
import com.marshmallow.hiring.instructions.exception.InvalidMovementException;
import com.marshmallow.hiring.instructions.model.CleaningResult;
import com.marshmallow.hiring.instructions.model.Instructions;
import org.springframework.stereotype.Service;

/**
 * Service class for the {@link InstructionsController} http controller.
 */
@Service
public class InstructionsService {

  /**
   * Creates a cleaning session using the provided information, and returns the cleaning results
   * after applying the provided instructions.
   *
   * @param instructions a description of the cleaning session and the operations to be performed in
   *                     it
   * @return a snapshot of the status of the area after performing the requested operations in it
   * @throws InvalidMovementException If the provided list of instructions would lead the cleaner
   *                                  outside the cleaning area
   */
  public CleaningResult calculateFinalState(Instructions instructions)
      throws InvalidMovementException {
    CleaningSession session = new CleaningSession(instructions.getAreaSize(),
        instructions.getStartingPosition(), instructions.getOilPatches());

    session.applyInstructions(instructions.getNavigationInstructions());

    return CleaningResult.builder().finalPosition(session.getCleanerPosition())
        .oilPatchesCleaned(session.getCleanedOilPatches().size()).build();
  }
}
