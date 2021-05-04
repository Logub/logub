package com.logub.logcontroller.domain.model.search;

import static lombok.AccessLevel.PRIVATE;

import com.logub.logcontroller.api.schema.LogubFieldTypeDto;
import com.logub.logcontroller.domain.model.schema.LogubFieldType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.util.List;

@Value
@AllArgsConstructor(access = PRIVATE)
@Builder(toBuilder = true)
public class LogubFieldSearch {
  @Builder.Default
  private LogubFieldType type = LogubFieldType.Tag;
  private String name;
  private List<String> values;
  private boolean negation;
}
