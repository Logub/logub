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
  private String service;
  @NonNull
  private SystemProperties systemProperties;
  @Builder.Default
  private Map<String, Object> businessProperties = Collections.emptyMap();
  @Builder.Default
  private Optional<String> message = Optional.empty();
  @Builder.Default
  private Instant timestamp = Instant.now();
  @NonNull
  private LogLevel level;
}
