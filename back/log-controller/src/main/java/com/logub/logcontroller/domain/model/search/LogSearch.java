package com.logub.logcontroller.domain.model.search;

import static java.util.Collections.emptyList;
import static lombok.AccessLevel.PRIVATE;

import com.logub.logcontroller.api.search.LogubFieldSearchDto;
import com.logub.logcontroller.domain.model.LogLevel;
import com.logub.logcontroller.domain.model.LogubSort;
import com.logub.logcontroller.domain.model.schema.LogubFieldType;
import com.logub.logcontroller.domain.model.search.LogubFieldSearch;
import com.logub.logcontroller.domain.query.redis.search.QueryBuilder;
import com.logub.logcontroller.domain.query.redis.search.QueryBuilders;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.SneakyThrows;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Value
@AllArgsConstructor(access = PRIVATE)
@Builder(toBuilder = true)
@Slf4j
public class LogSearch {

  @Builder.Default
  private List<LogubFieldSearch> texts = emptyList();
  @Builder.Default
  private List<LogubFieldSearch> systemProperties = emptyList();
  @Builder.Default
  private List<LogubFieldSearch> businessProperties = emptyList();
  @Builder.Default
  private List<LogubFieldSearch> levels = Collections.emptyList();
  @Builder.Default
  private int limit = 25;
  @Builder.Default
  private int offset = 0;
  @Builder.Default
  private Optional<LogubSort> sort = Optional.empty();
  @Builder.Default
  private Instant beginAt = Instant.now().minus(15, ChronoUnit.MINUTES);
  @Builder.Default
  private Instant endAt = Instant.now();


  @SneakyThrows
  public QueryBuilder toQuery() {
    var query = new QueryBuilder();
    var businessPrefix = "businessProperties.";
    var systemPropertiesPrefix = "systemProperties.";
    for (LogubFieldSearch properties : businessProperties) {
      query.append(QueryBuilders.tag(businessPrefix + properties.getName(), properties.getValues(),
          properties.isNegation()));
    }
    for (LogubFieldSearch properties : systemProperties) {
      query.append(QueryBuilders
          .tag(systemPropertiesPrefix + properties.getName(), properties.getValues(),
              properties.isNegation()));
    }
    if (!levels.isEmpty()) {
      for (LogubFieldSearch level : levels) {
        var onError = !level.getValues().stream().allMatch(v -> Arrays.stream(LogLevel.values())
            .anyMatch(enumLevel -> enumLevel.name().equalsIgnoreCase(v)));
        if(onError){
          log.error("bad payload for levels {}", level);
          throw new IllegalArgumentException("bad payload for level");
        }
        query.append(QueryBuilders.tag("level",level.getValues(), level.isNegation()));
      }
    }
    for (LogubFieldSearch text : texts) {
      if(!text.getType().equals(LogubFieldType.FullText)){
        log.warn("type {} not handle for text search", text.getType());
      }
      for (String value : text.getValues()) {
        query.append(QueryBuilders.text("message", value, text.isNegation()));
      }
    }

    return query;
  }


}
