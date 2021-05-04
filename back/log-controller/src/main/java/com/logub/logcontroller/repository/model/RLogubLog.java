package com.logub.logcontroller.repository.model;

import com.logub.logcontroller.domain.model.LogLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@RedisHash("log")
public class RLogubLog implements Serializable {
  @Builder.Default
  @Id
  private String id = UUID.randomUUID().toString();

  private String index = "principal";

  private String service;
  private RSystemProperties systemProperties;
  private Map<String, Object> businessProperties;
  private String message;
  private long timestamp = Instant.now().toEpochMilli();
  private LogLevel level;
}
