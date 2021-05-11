package com.loghub.loggenerator.dto;

import com.loghub.loggenerator.model.LogLevelEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * Representation of a Logub log before logging.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class LogubLogDto {

  /**
   * Business properties.
   */
  private Map<String, String> businessProperties;

  /**
   * Message to log.
   */
  private String message;

  /**
   * Log level.
   */
  private LogLevelEnum level;
}
