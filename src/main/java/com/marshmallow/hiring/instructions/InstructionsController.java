package com.marshmallow.hiring.instructions;

import com.marshmallow.hiring.instructions.model.CleaningResult;
import com.marshmallow.hiring.instructions.model.Instructions;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * HTTP Controller defining the api to calculate the result of a set of instructions on a given sea
 * space.
 * <p>
 * In normal circumstances this class would be abstracted behind an interface for optimal testing
 * capabilities and flexibility, but given the small scale of the current solution, it was
 * considered overkill.
 */
@Slf4j
@RequiredArgsConstructor
@RestController
public class InstructionsController {

  public static final String ENDPOINT_INSTRUCTIONS = "/instructions";

  private final InstructionsService instructionsService;

  // This is your entry point
  // You need to expose a POST endpoint on the "/instructions" path to match the contract expected by our tests.
  // If the existing test in the ControllerTest keeps failing it means it's not respecting this base contract.
  @PostMapping(
      value = ENDPOINT_INSTRUCTIONS,
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public CleaningResult instructions(@Valid @RequestBody Instructions instructions) {
    log.debug("Received: " + instructions.toString());
    return instructionsService.calculateFinalState(instructions);
  }
}
