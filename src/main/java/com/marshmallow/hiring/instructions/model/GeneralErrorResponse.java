package com.marshmallow.hiring.instructions.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import lombok.Builder;
import lombok.Data;

/**
 * Data transfer object used to return an error response in a consistent way across the various
 * endpoints provided.
 */
@Data
@Builder
public class GeneralErrorResponse {

  /**
   * Time when the error has occurred. If not provided, the lombok's builder will use the current
   * time.
   */
  @Builder.Default
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date time = new Date();

  /**
   * Short description of the error type
   */
  private String error;

  /**
   * A more informative message regarding the error
   */
  private String message;
}
