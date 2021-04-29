package com.logub.logcontroller.api.schema;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class LogubSortDto {
  private String field;
  private LogubOrderDto order;
  public static enum LogubOrderDto{
    ASC, DESC
  }
}
