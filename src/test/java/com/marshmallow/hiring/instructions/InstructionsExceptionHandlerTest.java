package com.marshmallow.hiring.instructions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import com.marshmallow.hiring.instructions.exception.InvalidArgumentException;
import com.marshmallow.hiring.instructions.exception.InvalidMovementException;
import com.marshmallow.hiring.instructions.model.GeneralErrorResponse;
import java.lang.reflect.Executable;
import org.junit.jupiter.api.Test;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;

@SuppressWarnings("ConstantConditions")
class InstructionsExceptionHandlerTest {

  private static final String TEST_MSG = "test message";

  private InstructionsExceptionHandler underTest = new InstructionsExceptionHandler();

  @Test
  void handleInvalidArgumentException() {
    // Arrange
    InvalidArgumentException exception = new InvalidArgumentException(TEST_MSG);

    // Act
    ResponseEntity<GeneralErrorResponse> response = underTest
        .handleInvalidArgumentException(exception);

    // Assert
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    GeneralErrorResponse actual = response.getBody();
    assertThat(actual.getError()).isEqualTo(HttpStatus.BAD_REQUEST.getReasonPhrase());
    assertThat(actual.getMessage()).isNotEqualTo(TEST_MSG);
  }

  @Test
  void handleInvalidMovementException() {
    // Arrange
    InvalidMovementException exception = new InvalidMovementException(TEST_MSG);

    // Act
    ResponseEntity<GeneralErrorResponse> response = underTest
        .handleInvalidMovementException(exception);

    // Assert
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    GeneralErrorResponse actual = response.getBody();
    assertThat(actual.getError()).isEqualTo(HttpStatus.BAD_REQUEST.getReasonPhrase());
    assertThat(actual.getMessage()).isNotEqualTo(TEST_MSG);
  }

  @Test
  void handleMethodArgumentNotValid() {
    // Arrange
    BindingResult bindingResult = new BeanPropertyBindingResult(TEST_MSG, TEST_MSG);
    ObjectError error = new FieldError("name", "name", TEST_MSG);
    bindingResult.addError(error);
    MethodParameter methodParameter = mock(MethodParameter.class);
    doReturn(mock(Executable.class)).when(methodParameter).getExecutable();
    MethodArgumentNotValidException exception = new MethodArgumentNotValidException(methodParameter, bindingResult);

    // Act
    ResponseEntity<Object> response = underTest
        .handleMethodArgumentNotValid(exception, new HttpHeaders(), HttpStatus.BAD_REQUEST, mock(
            WebRequest.class));

    // Assert
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    Object body = response.getBody();
    assertThat(body).isInstanceOf(GeneralErrorResponse.class);
    GeneralErrorResponse actual = (GeneralErrorResponse) body;
    assertThat(actual.getError()).isEqualTo(HttpStatus.BAD_REQUEST.getReasonPhrase());
    assertThat(actual.getMessage()).contains(TEST_MSG);
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
        .buildResponseEntityFromInternalError(exception, new HttpHeaders(),
            HttpStatus.INTERNAL_SERVER_ERROR);

    // Assert
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    assertThat(response.getBody()).isInstanceOf(GeneralErrorResponse.class);
    GeneralErrorResponse actual = (GeneralErrorResponse) response.getBody();
    expected.setTime(actual.getTime()); // Ignore time difference
    assertThat(actual).isEqualTo(expected);
  }
}