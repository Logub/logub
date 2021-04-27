package com.logub.logcontroller.api;

import static lombok.AccessLevel.PRIVATE;

import com.logub.logcontroller.domain.model.SystemProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Value;

import java.time.Instant;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class LogEventDto {
 private String service;
 private SystemProperties tags;
 private Map<String, Object> businessProperties;
 private Optional<String> message = Optional.empty();
 private Instant timestamp = Instant.now();
 private LogLevelDto level;
}
