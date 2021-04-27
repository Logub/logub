package com.logub.logcontroller.repository.model;

import com.logub.logcontroller.domain.model.LogLevel;
import com.logub.logcontroller.domain.model.SystemProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;
import java.util.Map;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class RLogEvent implements Serializable{
 private String service;
 private RSystemProperties tags;
 private Map<String, Object> businessProperties;
 private String message;
 private Instant timestamp = Instant.now();
 private LogLevel level;
}
