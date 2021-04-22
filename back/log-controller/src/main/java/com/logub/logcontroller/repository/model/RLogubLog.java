package com.logub.logcontroller.repository.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class RLogubLog {
  @Builder.Default
  private String id = UUID.randomUUID().toString();

  private String index = "principal";

  private RLogEvent event;
}
