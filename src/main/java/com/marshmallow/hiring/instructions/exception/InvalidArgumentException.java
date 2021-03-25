package com.marshmallow.hiring.instructions.exception;

/**
 * Generic exception to be thrown in case any invalid data has been sent as part of the http
 * request.
 * <p>
 * It extends {@link RuntimeException} to allow us to use it both inside our custom Json
 * deserializers and in other parts of our rest layer, if needed. The alternative would be to extend
 * IOException, which would be incorrect from a semantic point of view.
 */
public class InvalidArgumentException extends RuntimeException {

  /**
   * Primary constructor. It is essential that no internal information, like stack trace or
   * references to the code, are included in the provided message.
   */
  public InvalidArgumentException(String message) {
    super(message);
  }
}
