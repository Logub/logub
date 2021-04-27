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

@Value
@AllArgsConstructor(access = PRIVATE)
@Builder(toBuilder = true)
public class LogEvent {
 @NonNull
 private String service;
 @NonNull
 private SystemProperties tags;
 @Builder.Default
 private Map<String, Object> businessProperties = Collections.emptyMap();
 @Builder.Default
 private Optional<String> message = Optional.empty();
 @Builder.Default
 private Instant timestamp = Instant.now();
 @NonNull
 private LogLevel level;
}
