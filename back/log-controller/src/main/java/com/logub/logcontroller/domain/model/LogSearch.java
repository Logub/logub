package com.logub.logcontroller.domain.model;

import static lombok.AccessLevel.PRIVATE;

import com.logub.logcontroller.api.schema.LogubSortDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.SneakyThrows;
import lombok.Value;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
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
  private Map<String, String> businessProperties;
  @Builder.Default
  private int limit = 25;
  @Builder.Default
  private int offset = 0;
  @Builder.Default
  private List<LogLevel> levels = Collections.emptyList();
  @Builder.Default
  private Optional<LogubSort> sort = Optional.empty();
  private String[] toIgnoreChar = {".", "-"};


  @SneakyThrows
  public String toQuery() {
    var query = new StringBuilder();
    var businessPrefix = "@event.businessProperties";
    var tagsPrefix = "@event.tags";
    for (Map.Entry<String, String> properties : businessProperties.entrySet()) {
      query.append(businessPrefix)
          .append(".")
          .append(properties.getKey())
          .append(":").append("{").append(properties.getValue()).append("} ");
    }
    if (tags.isPresent()) {
      for (Field declaredField : SystemProperties.class.getDeclaredFields()) {
        Method declaredMethod = SystemProperties.class
            .getDeclaredMethod("get" + StringUtils.capitalize(declaredField.getName()), null);
        Optional<String> value = (Optional<String>) declaredMethod.invoke(tags.get(), null);
        if (value.isPresent()) {

          query.append(tagsPrefix)
              .append(".")
              .append(declaredField.getName())
              .append(":").append("{").append(value.get()).append("} ");
        }
      }
    }
    if(!levels.isEmpty()) {
      query.append(
          orTag("event.level", levels.stream()
              .map(v -> v.toString()).collect(Collectors.toList())));
    }

    text.ifPresent(v -> {
      v = v.trim();
      if (v.startsWith("*")) {
        v = v.substring(1);
      }
      if(!v.isBlank()) {
        query.append(" @event.message:").append(v);
      }
    });
    String finalQuery = query.toString();
    for (String forbiddenChar : toIgnoreChar) {
      finalQuery = finalQuery.replace(forbiddenChar, "\\" + forbiddenChar);
    }
    return finalQuery;
  }

  public String orTag(String prefix, List<String> values) {
    StringBuilder orTagQuery = new StringBuilder().append('@').append(prefix).append(":{");
    for (int i = 0; i < values.size(); i++) {
      orTagQuery.append(values.get(i));
      if (i != values.size() - 1) {
        orTagQuery.append('|');
      }
    }
    orTagQuery.append("}");
    return orTagQuery.toString();
  }
}
