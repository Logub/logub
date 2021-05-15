package com.logub.logcontroller.domain.query.redis.search;

import io.redisearch.Query;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class QueryBuilders {

  public static QueryBuilder tag(String field, String value, boolean negative) {
    return tag(field, Collections.singletonList(value), negative);
  }

  public static QueryBuilder tag(String field, boolean negative, String... values) {
    return tag(field, Arrays.asList(values), negative);
  }

  public static QueryBuilder tag(String field, List<String> values, boolean negative) {
    QueryBuilder queryBuilder =
        new QueryBuilder().append(fieldToRedisField(cleanString(field), negative)).append(":{");
    for (int i = 0; i < values.size(); i++) {
      queryBuilder.append(cleanString(values.get(i)));
      if (i != values.size() - 1) {
        queryBuilder.append('|');
      }
    }
    queryBuilder.append('}');
    return queryBuilder;
  }

  public static QueryBuilder text(String field, String text, boolean negative) {
    QueryBuilder queryBuilder =
        new QueryBuilder().append(fieldToRedisField(cleanString(field), negative)).append(':');
    text = text.trim();
    if (text.startsWith("*")) {
      text = text.substring(1);
    }
    if (!text.isBlank()) {
      queryBuilder.append(cleanString(text));
    }
    return queryBuilder;
  }

  public static Query.Filter filterNumeric(String field, Instant beginAt, Instant endAt) {
    return new Query.NumericFilter(field, beginAt.toEpochMilli(), endAt.toEpochMilli());
  }

  private static String fieldToRedisField(String field, boolean negative) {
    field = '@' + field;
    if (negative) {
      field = '-' + field;
    }
    return field;
  }
  public static String cleanString(String value){
    for (char c : "\\,/.<>{}[]\"':;!@#$%^&()-+=~".toCharArray()) {
      value = value.replace(""+c, "\\"+c);
    }
    return value;
  }
}
