package com.logub.logcontroller.api.search;

import static java.util.Collections.emptyList;

import com.logub.logcontroller.api.schema.LogubSortDto;
import com.logub.logcontroller.domain.model.search.LogubFieldSearch;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class LogSearchDto {

  @Builder.Default
  private List<LogubFieldSearchDto> texts = emptyList();

  @Builder.Default
  private List<LogubFieldSearchDto> systemProperties = emptyList();

  @Builder.Default
  private List<LogubFieldSearchDto> businessProperties = emptyList();

  @Builder.Default
  private List<LogubFieldSearch> basicProperties = emptyList();

  @Builder.Default
  private int limit = 25;
  @Builder.Default
  private int offset = 0;
  @Builder.Default
  private List<LogubFieldSearchDto> levels = Collections.emptyList();
  @Builder.Default
  private Optional<LogubSortDto> sort = Optional.empty();
  @Builder.Default
  private Instant beginAt = Instant.now().minus(15, ChronoUnit.MINUTES);
  @Builder.Default
  private Instant endAt = Instant.now();

}
