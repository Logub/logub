package com.logub.logcontroller.api;

import com.logub.logcontroller.api.schema.LogubSortDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class LogSearchDto {
  @Builder.Default
  private Optional<String> text = Optional.empty();
  @Builder.Default
  private Optional<SystemPropertiesDto> tags = Optional.empty();

  @Builder.Default
  private Map<String, List<String>> businessProperties = Collections.emptyMap();
  @Builder.Default
  private int limit = 25;
  @Builder.Default
  private int offset = 0;
  @Builder.Default
  private List<LogLevelDto> levels = Collections.emptyList();
  @Builder.Default
  private Optional<LogubSortDto> sort = Optional.empty();
  @Builder.Default
  private Instant beginAt = Instant.now().minus(15, ChronoUnit.MINUTES);
  @Builder.Default
  private Instant endAt = Instant.now();

}
