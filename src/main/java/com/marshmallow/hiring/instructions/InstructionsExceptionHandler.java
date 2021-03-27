package com.marshmallow.hiring.instructions;

import com.fasterxml.jackson.core.JsonParseException;
import com.marshmallow.hiring.instructions.exception.InvalidArgumentException;
import com.marshmallow.hiring.instructions.exception.InvalidMovementException;
import com.marshmallow.hiring.instructions.model.GeneralErrorResponse;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.NestedRuntimeException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Spring exception handler component
 */
@Slf4j
@ControllerAdvice
public class InstructionsExceptionHandler extends ResponseEntityExceptionHandler {

  private static final String INVALID_ARGUMENT_PREFIX = "Invalid argument received: ";
  private static final String INVALID_MOVEMENT_MESSAGE = "The provided instructions would lead the cleaner out of the specified cleaning area. Please check them and try again as we don't want to waste drones recovering lost cleaners...";
  private static final String PARSING_ERROR = "An error occurred while parsing the request body";
  private static final String GENERIC_INTERNAL_ERROR = "An internal error occurred";

  /**
   * Provides handling of {@link InvalidArgumentException}
   *
   * @param e - runtime InvalidArgumentException
   * @return - error response with exception message in the body
   */
  @ExceptionHandler(InvalidArgumentException.class)
  public ResponseEntity<GeneralErrorResponse> handleInvalidArgumentException(
      InvalidArgumentException e) {
    log.error("Exception mapper caught InvalidArgumentException. Mapping to error response...", e);

    // It is safe to include the exception message for this specific exception because it is
    // specifically designed to only contain information to be exposed to the outside.
    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(GeneralErrorResponse.builder()
            .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
            .message(INVALID_ARGUMENT_PREFIX + e.getMessage())
            .build());
  }

  /**
   * Provides handling of {@link InvalidMovementException}
   *
   * @param e - runtime InvalidMovementException
   * @return - error response with exception message in the body
   */
  @ExceptionHandler(InvalidMovementException.class)
  public ResponseEntity<GeneralErrorResponse> handleInvalidMovementException(
      InvalidMovementException e) {
    log.error("Instructions service caught InvalidMovementException. Mapping to error response...",
        e);

    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(GeneralErrorResponse.builder()
            .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
            .message(INVALID_MOVEMENT_MESSAGE)
            .build());
  }

  /**
   * Provides handling of {@link MethodArgumentNotValidException} This method needed to override the
   * existing one to avoid the "Ambiguous @ExceptionHandler method mapped" error.
   *
   * @param ex - runtime MethodArgumentNotValidException
   * @return - error response with exception message in the body
   */
  @Override
  public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
      HttpHeaders headers, HttpStatus status, WebRequest request) {
    log.error(
        "Spring Validation caught MethodArgumentNotValidException. Mapping to error response...",
        ex);

    BindingResult bindingResult = ex.getBindingResult();
    List<FieldError> fieldErrors = bindingResult.getFieldErrors();

    String message = fieldErrors.stream()
        .map(fieldError -> INVALID_ARGUMENT_PREFIX + fieldError.getField() + ". Reason: "
            + fieldError.getDefaultMessage())
        .collect(Collectors.joining(System.getProperty("line.separator")));

    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(GeneralErrorResponse.builder()
            .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
            .message(message)
            .build());
  }

  /**
   * Provides handling of Generic Exception.
   *
   * @param e - generic exception
   * @return error response with default message in the body.
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<GeneralErrorResponse> handleException(Exception e) {

    log.error("Generic application exception. Details: {}", e.getMessage(), e);
    return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(GeneralErrorResponse.builder()
            .error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
            .message("An internal error has occurred")
            .build());
  }

  /**
   * Overrides default Spring behaviour and return JSON error response in case of exceptions
   * generated inside the framework (i.e.: a custom JsonDeserializer).
   */
  @Override
  protected ResponseEntity<Object> handleExceptionInternal(
      Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
    log.error("Spring handled exception occurred", ex);

    return buildResponseEntityFromInternalError(ex, headers, status);
  }

  // Visible for testing
  protected ResponseEntity<Object> buildResponseEntityFromInternalError(Exception ex,
      HttpHeaders headers, HttpStatus status) {
    String message = GENERIC_INTERNAL_ERROR;

    // If root cause was thrown by our custom deserializer, include its explanation message
    if (ex instanceof NestedRuntimeException) {
      NestedRuntimeException httpEx = (NestedRuntimeException) ex;
      Throwable root = httpEx.getRootCause();
      if (root instanceof InvalidArgumentException) {
        message = INVALID_ARGUMENT_PREFIX + root.getMessage();
      } else if (root instanceof JsonParseException) {
        message = PARSING_ERROR;
      }
    }

    return ResponseEntity.status(status)
        .headers(headers)
        .body(GeneralErrorResponse.builder()
            .error(status.getReasonPhrase())
            .message(message)
            .build());
  }
}