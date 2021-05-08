package com.logub.logcontroller.api;

import com.logub.logcontroller.domain.model.SystemProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class LogubLogDto {
  @Builder.Default
  private String id = UUID.randomUUID().toString();

  private String index = "principal";
  private SystemProperties systemProperties;
  private Map<String, Object> businessProperties;
  private Optional<String> message = Optional.empty();
  private Instant timestamp = Instant.now();
  @Builder.Default
  private Optional<String>  service = Optional.empty();

  @Builder.Default
  private Optional<String> logger = Optional.empty();

  @Builder.Default
  private Optional<String> thread = Optional.empty();
  @Builder.Default
  private Optional<String> source = Optional.empty();
  private LogLevelDto level;
}
