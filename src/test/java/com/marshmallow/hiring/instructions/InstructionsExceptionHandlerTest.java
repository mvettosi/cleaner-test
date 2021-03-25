package com.marshmallow.hiring.instructions;

import static org.assertj.core.api.Assertions.assertThat;

import com.marshmallow.hiring.instructions.exception.InvalidArgumentException;
import com.marshmallow.hiring.instructions.model.GeneralErrorResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;

@SuppressWarnings("ConstantConditions")
class InstructionsExceptionHandlerTest {

  private static final String TEST_MSG = "test message";

  private InstructionsExceptionHandler underTest = new InstructionsExceptionHandler();

  @Test
  void handleInvalidArgumentException() {
    // Arrange
    GeneralErrorResponse expected = GeneralErrorResponse.builder().error("Bad Request")
        .message("Invalid argument received: " + TEST_MSG).build();
    InvalidArgumentException exception = new InvalidArgumentException(TEST_MSG);

    // Act
    ResponseEntity<GeneralErrorResponse> response = underTest
        .handleInvalidArgumentException(exception);

    // Assert
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    GeneralErrorResponse actual = response.getBody();
    expected.setTime(actual.getTime()); // Ignore time difference
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void handleException() {
    // Arrange
    GeneralErrorResponse expected = GeneralErrorResponse.builder().error("Internal Server Error")
        .message("An internal error has occurred").build();
    Exception exception = new Exception(TEST_MSG);

    // Act
    ResponseEntity<GeneralErrorResponse> response = underTest.handleException(exception);

    // Assert
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    GeneralErrorResponse actual = response.getBody();
    expected.setTime(actual.getTime()); // Ignore time difference
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void buildResponseEntityFromInternalError_InvalidArgument() {
    // Arrange
    GeneralErrorResponse expected = GeneralErrorResponse.builder().error("Bad Request")
        .message("Invalid argument received: " + TEST_MSG).build();
    HttpMessageNotReadableException exception = new HttpMessageNotReadableException("Prefix: ",
        new InvalidArgumentException(TEST_MSG), null);

    // Act
    ResponseEntity<Object> response = underTest
        .buildResponseEntityFromInternalError(exception, new HttpHeaders(), HttpStatus.BAD_REQUEST);

    // Assert
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(response.getBody()).isInstanceOf(GeneralErrorResponse.class);
    GeneralErrorResponse actual = (GeneralErrorResponse) response.getBody();
    expected.setTime(actual.getTime()); // Ignore time difference
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void buildResponseEntityFromInternalError_GeneralException() {
    // Arrange
    GeneralErrorResponse expected = GeneralErrorResponse.builder().error("Internal Server Error")
        .message("An internal error occurred").build();
    HttpMessageNotReadableException exception = new HttpMessageNotReadableException("Prefix: ",
        new RuntimeException(TEST_MSG), null);

    // Act
    ResponseEntity<Object> response = underTest
        .buildResponseEntityFromInternalError(exception, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);

    // Assert
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    assertThat(response.getBody()).isInstanceOf(GeneralErrorResponse.class);
    GeneralErrorResponse actual = (GeneralErrorResponse) response.getBody();
    expected.setTime(actual.getTime()); // Ignore time difference
    assertThat(actual).isEqualTo(expected);
  }
}