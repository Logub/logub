package com.logub.logcontroller.api;

import com.logub.logcontroller.domain.model.LogEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Collections;
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

  private LogEventDto event;
}
