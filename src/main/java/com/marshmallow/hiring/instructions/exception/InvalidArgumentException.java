package com.marshmallow.hiring.instructions.exception;

/**
 * Generic exception to be thrown in case any invalid data has been sent as part of the http
 * request.
 */
public class InvalidArgumentException extends Exception {

  /**
   * Primary constructor. It is essential that no internal information, like stack trace or
   * references to the code, are included in the provided message.
   */
  public InvalidArgumentException(String message) {
    super(message);
  }
}
