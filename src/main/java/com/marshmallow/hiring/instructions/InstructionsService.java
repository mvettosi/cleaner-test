package com.marshmallow.hiring.instructions;

import com.marshmallow.hiring.instructions.business.CleaningSession;
import com.marshmallow.hiring.instructions.exception.InvalidArgumentException;
import com.marshmallow.hiring.instructions.exception.InvalidMovementException;
import com.marshmallow.hiring.instructions.model.CleaningResult;
import com.marshmallow.hiring.instructions.model.Instructions;
import com.marshmallow.hiring.instructions.model.InstructionsRequest;
import com.marshmallow.hiring.instructions.model.Position;
import com.marshmallow.hiring.instructions.parsers.NavigationParser;
import com.marshmallow.hiring.instructions.parsers.PositionParser;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service class for the {@link InstructionsController} http controller.
 */
@Service
@RequiredArgsConstructor
public class InstructionsService {

  private final NavigationParser navigationParser;
  private final PositionParser positionParser;

  /**
   * Creates a cleaning session using the provided information, and returns the cleaning results
   * after applying the provided instructions.
   *
   * @param instructionsRequest a description of the cleaning session and the operations to be
   *                            performed in it
   * @return a snapshot of the status of the area after performing the requested operations in it
   * @throws InvalidArgumentException if the provided instructions request is invalid
   * @throws InvalidMovementException If the provided list of instructions would lead the cleaner
   *                                  outside the cleaning area
   */
  public CleaningResult calculateFinalState(InstructionsRequest instructionsRequest)
      throws InvalidMovementException, InvalidArgumentException {
    // Parse the request body and create a session for it
    Instructions instructions = parse(instructionsRequest);
    CleaningSession session = new CleaningSession(instructions.getAreaSize(),
        instructions.getStartingPosition(), instructions.getOilPatches());

    // Apply the instructions and retrieve the final cleaner position
    session.applyInstructions(instructions.getNavigationInstructions());
    Position finalPosition = session.getCleanerPosition();

    // Create and return a response body
    return CleaningResult.builder()
        .finalPosition(List.of(finalPosition.getX(), finalPosition.getY()))
        .oilPatchesCleaned(session.getCleanedOilPatches().size()).build();
  }

  private Instructions parse(InstructionsRequest instructionsRequest)
      throws InvalidArgumentException {
    return Instructions.builder()
        .areaSize(positionParser.parse(instructionsRequest.getAreaSize()))
        .startingPosition(positionParser.parse(instructionsRequest.getStartingPosition()))
        .oilPatches(positionParser.parseMultiple(instructionsRequest.getOilPatches()))
        .navigationInstructions(
            navigationParser.parse(instructionsRequest.getNavigationInstructions()))
        .build();
  }
}
