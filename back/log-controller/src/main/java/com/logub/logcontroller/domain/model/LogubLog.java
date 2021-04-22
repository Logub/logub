package com.logub.logcontroller.domain.model;

import static lombok.AccessLevel.PRIVATE;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@AllArgsConstructor(access = PRIVATE)
@Builder(toBuilder = true)
public class LogubLog {
  @Builder.Default
  private String id = UUID.randomUUID().toString();

  @Builder.Default
  private String index = "principal";

  @Builder.Default
  private LogEvent event = LogEvent.builder().build();
}
