package com.marshmallow.hiring.instructions;

import com.marshmallow.hiring.instructions.model.Instructions;
import com.marshmallow.hiring.instructions.model.CleaningResult;
import org.springframework.stereotype.Service;

@Service
public class InstructionsService {

  public CleaningResult calculateFinalState(Instructions instructions) {
    return CleaningResult.builder().finalPosition(instructions.getAreaSize()).oilPatchesCleaned(1).build();
  }
}
