package com.logub.logcontroller.domain.model.schema;

import static lombok.AccessLevel.PRIVATE;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor(access = PRIVATE)
@Builder(toBuilder = true)
public class BusinessField {
  private LogubFieldType type;
  private String name;



}
