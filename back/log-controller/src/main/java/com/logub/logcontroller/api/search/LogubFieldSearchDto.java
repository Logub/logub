package com.logub.logcontroller.api.search;

import com.logub.logcontroller.api.schema.LogubFieldTypeDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class LogubFieldSearchDto {
  @Builder.Default
  private LogubFieldTypeDto type = LogubFieldTypeDto.Tag;
  private String name;
  private List<String> values;
  private boolean negation;
}
