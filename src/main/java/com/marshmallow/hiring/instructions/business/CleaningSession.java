package com.marshmallow.hiring.instructions.business;

import com.marshmallow.hiring.instructions.exception.InvalidArgumentException;
import com.marshmallow.hiring.instructions.exception.InvalidMovementException;
import com.marshmallow.hiring.instructions.model.NavigationType;
import com.marshmallow.hiring.instructions.model.Position;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.Getter;
import lombok.NonNull;

/**
 * Class representing a cleaning operation.
 */
@Getter
public class CleaningSession {

  private final Position areaSize;
  private final Position cleanerPosition;
  private final Set<Position> remainingOilPatches;
  private final Set<Position> cleanedOilPatches = new HashSet<>();

  /**
   * Class constructor. All arguments are required to be non null.
   *
   * @param areaSize            the sea area to operate on
   * @param cleanerPosition     the initial position of the cleaner. This is required to be within
   *                            the provided area size
   * @param remainingOilPatches set of oil patches present in the area. Currently, no error is risen
   *                            for oil patches outside of the provided area, they are simply
   *                            ignored. The provided set is copied into an internal mutable one, so
   *                            no modifications are to be expected on the original one.
   */
  public CleaningSession(@NonNull Position areaSize,
      @NonNull Position cleanerPosition,
      @NonNull Set<Position> remainingOilPatches) {
    this.areaSize = areaSize;
    this.cleanerPosition = cleanerPosition;
    this.remainingOilPatches = new HashSet<>(remainingOilPatches);

    if (isCleanerOutsideBoundaries()) {
      throw new InvalidArgumentException("The starting cleaner position is outside the sea area");
    }

    // If the cleaner starts on an oil patch, automatically clean it.
    cleanCurrentPosition();
  }

  /**
   * Main operational method, that attempts to operate a list of navigation instructions on the
   * cleaner, within the dedicates sea area.
   * <p>
   * Small Note: in a real life robot-controlling system, it would be likely for this logic to be
   * repeated twice: once to project the outcome of the whole operation and warn the user of
   * possible errors (like, stepping outside boundaries), and the second time to actually remotely
   * move the cleaner step by step. A requirement like this, however, was never specifically
   * mentioned, so for simplicity's sake the cleaner is being operated directly.
   *
   * @param navigationInstructions A list of navigation instructions to be operated on the cleaner.
   * @throws InvalidMovementException If any of the provided instructions would move the cleaner
   *                                  outside the sea area boundaries.
   */
  public void applyInstructions(@NonNull List<NavigationType> navigationInstructions)
      throws InvalidMovementException {
    for (NavigationType instruction : navigationInstructions) {
      cleanerPosition.move(instruction);

      if (isCleanerOutsideBoundaries()) {
        throw new InvalidMovementException(
            "The list of provided navigation instructions took the cleaner outside the given boundaries. Aborting.");
      }

      cleanCurrentPosition();
    }
  }

  /**
   * Utility method to mark a patch as cleaned, if it contained an oil patch.
   */
  private void cleanCurrentPosition() {
    if (remainingOilPatches.contains(cleanerPosition)) {
      remainingOilPatches.remove(cleanerPosition);
      cleanedOilPatches.add(cleanerPosition);
    }
  }

  private boolean isCleanerOutsideBoundaries() {
    return cleanerPosition.getX() < 0 || cleanerPosition.getX() > areaSize.getX()
        || cleanerPosition.getY() < 0 || cleanerPosition.getY() > areaSize.getY();
  }
}
