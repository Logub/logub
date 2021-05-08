package com.logub.logcontroller.domain.model;

import static lombok.AccessLevel.PRIVATE;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.time.Instant;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Value
@AllArgsConstructor(access = PRIVATE)
@Builder(toBuilder = true)
public class LogubLog {
  @Builder.Default
  private String id = UUID.randomUUID().toString();

  @Builder.Default
  private String index = "principal";

  @NonNull
  private SystemProperties systemProperties;

  @Builder.Default
  private Map<String, Object> businessProperties = Collections.emptyMap();

  @Builder.Default
  private Optional<String> message = Optional.empty();

  @Builder.Default
  private Instant timestamp = Instant.now();

  @Builder.Default
  private Optional<String>  service = Optional.empty();

  @Builder.Default
  private Optional<String> logger = Optional.empty();

  @Builder.Default
  private Optional<String> thread = Optional.empty();
  @Builder.Default
  private Optional<String> source = Optional.empty();
  @NonNull
  private LogLevel level;
}
