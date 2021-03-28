package com.marshmallow.hiring.instructions;

import static com.marshmallow.hiring.instructions.InstructionsController.ENDPOINT_INSTRUCTIONS;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_OCTET_STREAM;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.marshmallow.hiring.instructions.model.Instructions;
import java.lang.reflect.Executable;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

@WebMvcTest(InstructionsController.class)
class InstructionsControllerTest {

  private static final MockHttpServletRequestBuilder REQUEST_BUILDER =
      request(HttpMethod.POST, ENDPOINT_INSTRUCTIONS)
          .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
          .accept(APPLICATION_JSON);
  private static final String BASIC_CASE_REQUEST =
      "{\"areaSize\": [5, 5], \"startingPosition\": [1, 2], \"oilPatches\": [[1, 0], [2, 2], [2, 3]], \"navigationInstructions\": \"NNESEESWNWW\"}";

  @Autowired
  private MockMvc mockMvc;
  @MockBean
  private InstructionsService instructionsService;

  // This test is here to ensure you have expose an endpoint on the right path for our automated tests running once you submit.
  // Please don't change it.
  // Feel free to add any other tests to this file or others.
  @Test
  void ensuresCorrectEndpointIsExposed() throws Exception {
    mockMvc.perform(REQUEST_BUILDER.content(BASIC_CASE_REQUEST))
        .andExpect(status().isOk());
  }

  @Test
  void ensureMissingContentTypeIsRejected() throws Exception {
    // Arrange
    MockHttpServletRequestBuilder builder = request(HttpMethod.POST, ENDPOINT_INSTRUCTIONS);

    // Act
    // Assert
    ResultActions resultActions = mockMvc.perform(builder.content(BASIC_CASE_REQUEST))
        .andExpect(status().isUnsupportedMediaType());
    checkErrorBody(resultActions);
  }

  @Test
  void ensureWrongContentTypeIsRejected() throws Exception {
    // Arrange
    MockHttpServletRequestBuilder builder = request(HttpMethod.POST, ENDPOINT_INSTRUCTIONS)
        .header(CONTENT_TYPE, APPLICATION_OCTET_STREAM);

    // Act
    // Assert
    ResultActions resultActions = mockMvc.perform(builder.content(BASIC_CASE_REQUEST))
        .andExpect(status().isUnsupportedMediaType());
    checkErrorBody(resultActions);
  }

  @Test
  void ensureMissingRequiredFieldIsRejected() throws Exception {
    // Arrange
    String requestBody = "{\"startingPosition\": [1, 2], \"oilPatches\": [[1, 0], [2, 2], [2, 3]], \"navigationInstructions\": \"NNESEESWNWW\"}";

    // Act
    // Assert
    ResultActions resultActions = mockMvc.perform(REQUEST_BUILDER.content(requestBody))
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(APPLICATION_JSON_VALUE));
    checkErrorBody(resultActions);
  }

  @Test
  void ensureWrongPositionFormatIsRejected() throws Exception {
    // Arrange
    String requestBody = "{\"areaSize\": \"wrong\", \"startingPosition\": [1, 2], \"oilPatches\": [[1, 0], [2, 2], [2, 3]], \"navigationInstructions\": \"NNESEESWNWW\"}";

    // Act
    // Assert
    ResultActions resultActions = mockMvc.perform(REQUEST_BUILDER.content(requestBody))
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(APPLICATION_JSON_VALUE));
    checkErrorBody(resultActions);
  }

  @Test
  void ensureWrongCoordinateTypeIsRejected() throws Exception {
    // Arrange
    String requestBody = "{\"areaSize\": [5, \"a\"], \"startingPosition\": [1, 2], \"oilPatches\": [[1, 0], [2, 2], [2, 3]], \"navigationInstructions\": \"NNESEESWNWW\"}";

    // Act
    // Assert
    ResultActions resultActions = mockMvc.perform(REQUEST_BUILDER.content(requestBody))
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(APPLICATION_JSON_VALUE));
    checkErrorBody(resultActions);
  }

  @Test
  void ensureTooManyCoordinatesIsRejected() throws Exception {
    // Arrange
    String requestBody = "{\"areaSize\": [5, 2, 3], \"startingPosition\": [1, 2], \"oilPatches\": [[1, 0], [2, 2], [2, 3]], \"navigationInstructions\": \"NNESEESWNWW\"}";

    // Act
    // Assert
    ResultActions resultActions = mockMvc.perform(REQUEST_BUILDER.content(requestBody))
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(APPLICATION_JSON_VALUE));
    checkErrorBody(resultActions);
  }

  @Test
  void ensureToofewCoordinatesIsRejected() throws Exception {
    // Arrange
    String requestBody = "{\"areaSize\": [5], \"startingPosition\": [1, 2], \"oilPatches\": [[1, 0], [2, 2], [2, 3]], \"navigationInstructions\": \"NNESEESWNWW\"}";

    // Act
    // Assert
    ResultActions resultActions = mockMvc.perform(REQUEST_BUILDER.content(requestBody))
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(APPLICATION_JSON_VALUE));
    checkErrorBody(resultActions);
  }

  @Test
  void ensureUnrecognisedInstructionIsRejected() throws Exception {
    // Arrange
    String requestBody = "{\"areaSize\": [5, 5], \"startingPosition\": [1, 2], \"oilPatches\": [[1, 0], [2, 2], [2, 3]], \"navigationInstructions\": \"NNESEESAWNWW\"}";

    // Act
    // Assert
    ResultActions resultActions = mockMvc.perform(REQUEST_BUILDER.content(requestBody))
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(APPLICATION_JSON_VALUE));
    checkErrorBody(resultActions);
  }

  @Test
  void ensureUnexpectedInternalErrorIsHandledCorrectly() throws Exception {
    // Arrange
    String requestBody = "{\"areaSize\": [5, 5], \"startingPosition\": [1, 2], \"oilPatches\": [[1, 0], [2, 2], [2, 3]], \"navigationInstructions\": \"NNESEESWNWW\"}";
    doThrow(new OutOfMemoryError()).when(instructionsService).calculateFinalState(any(
        Instructions.class));

    // Act
    // Assert
    ResultActions resultActions = mockMvc.perform(REQUEST_BUILDER.content(requestBody))
        .andExpect(status().isInternalServerError())
        .andExpect(content().contentType(APPLICATION_JSON_VALUE));
    checkErrorBody(resultActions);
  }

  private void checkErrorBody(ResultActions resultActions) throws Exception {
    resultActions.andExpect(jsonPath("$.*", hasSize(3)))
        .andExpect(jsonPath("$.time").isString())
        .andExpect(jsonPath("$.error").isString())
        .andExpect(jsonPath("$.message").isString());
  }
}