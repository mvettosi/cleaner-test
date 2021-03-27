package com.marshmallow.hiring.instructions.exception;

/**
 * Exception to be thrown when attempting to perform an invalid navigation command.
 */
public class InvalidMovementException extends Exception {

  public InvalidMovementException(String message) {
    super(message);
  }
}
