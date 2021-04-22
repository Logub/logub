package com.logub.logcontroller.repository.model;

import com.logub.logcontroller.domain.model.SystemProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class RLogEvent {
 private String service;
 private SystemProperties tags;
 private Map<String, Object> businessProperties;
 private Optional<String> message = Optional.empty();
 private Instant timestamp = Instant.now();
 private String level;
}
