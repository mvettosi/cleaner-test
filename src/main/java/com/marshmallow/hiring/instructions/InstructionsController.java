package com.marshmallow.hiring.instructions;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InstructionsController {

    // TODO This is your entry point
    // You need to expose a POST endpoint on the "/instructions" path to match the contract expected by our tests.
    // If the existing test in the ControllerTest keeps failing it means it's not respecting this base contract.
    @RequestMapping("/instructions")
    public String instructions() {
        return "{\n" +
                "  \"finalPosition\" : [1, 3],\n" +
                "  \"oilPatchesCleaned\" : 1\n" +
                "}";
    }

}
