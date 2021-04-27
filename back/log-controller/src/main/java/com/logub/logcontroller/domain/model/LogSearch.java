package com.logub.logcontroller.domain.model;

import static lombok.AccessLevel.PRIVATE;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.util.Optional;

@Value
@AllArgsConstructor(access = PRIVATE)
@Builder(toBuilder = true)
public class LogSearch {

  private Optional<String> text;
}
