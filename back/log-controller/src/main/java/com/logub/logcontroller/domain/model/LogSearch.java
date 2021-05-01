package com.logub.logcontroller.domain.model;

import static lombok.AccessLevel.PRIVATE;

import com.logub.logcontroller.domain.query.redis.search.QueryBuilder;
import com.logub.logcontroller.domain.query.redis.search.QueryBuilders;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.SneakyThrows;
import lombok.Value;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Value
@AllArgsConstructor(access = PRIVATE)
@Builder(toBuilder = true)
public class LogSearch {

  private Optional<String> text;
  private Optional<SystemProperties> tags;
  private Map<String, List<String>> businessProperties;
  @Builder.Default
  private int limit = 25;
  @Builder.Default
  private int offset = 0;
  @Builder.Default
  private List<LogLevel> levels = Collections.emptyList();
  @Builder.Default
  private Optional<LogubSort> sort = Optional.empty();
  @Builder.Default
  private Instant beginAt = Instant.now().minus(15, ChronoUnit.MINUTES);
  @Builder.Default
  private Instant endAt = Instant.now();



  @SneakyThrows
  public QueryBuilder toQuery() {
    var query = new QueryBuilder();
    var businessPrefix = "event.businessProperties.";
    var tagsPrefix = "event.tags.";
    for (Map.Entry<String, List<String>> properties : businessProperties.entrySet()) {
      query.append(QueryBuilders.tag(businessPrefix + properties.getKey(),
          properties.getValue()));
    }
    if (tags.isPresent()) {
      for (Field declaredField : SystemProperties.class.getDeclaredFields()) {
        Method declaredMethod = SystemProperties.class
            .getDeclaredMethod("get" + StringUtils.capitalize(declaredField.getName()), null);
        Optional<String> value = (Optional<String>) declaredMethod.invoke(tags.get(), null);
        if (value.isPresent()) {
          query.append(QueryBuilders.tag(tagsPrefix+declaredField.getName(),value.get()));
        }
      }
    }
    if (!levels.isEmpty()) {
      query.append(QueryBuilders.tag("event.level", levels.stream()
              .map(v -> v.toString()).collect(Collectors.toList())));
    }

    text.ifPresent(v -> {
      if(!(v.isEmpty() || v.isBlank())) {
        query.append(QueryBuilders.text("event.message", v));
      }
    });
    return query;
  }



}
