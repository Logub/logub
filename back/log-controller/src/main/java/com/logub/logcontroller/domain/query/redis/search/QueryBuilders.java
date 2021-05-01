package com.logub.logcontroller.domain.query.redis.search;

import io.redisearch.Query;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class QueryBuilders {

  public static QueryBuilder tag(String field, String value) {
    return tag(field, Collections.singletonList(value));
  }
  public static QueryBuilder tag(String field, String ... values) {
    return tag(field, Arrays.asList(values));
  }
  public static QueryBuilder tag(String field, List<String> values) {
    QueryBuilder queryBuilder = new QueryBuilder().append('@').append(field).append(":{");
    for (int i = 0; i < values.size(); i++) {
      queryBuilder.append(values.get(i));
      if (i != values.size() - 1) {
        queryBuilder.append('|');
      }
    }
    queryBuilder.append('}');
    return queryBuilder;
  }

  public static QueryBuilder text(String field, String text) {
    QueryBuilder queryBuilder = new QueryBuilder().append('@').append(field).append(':');
    text = text.trim();
    if (text.startsWith("*")) {
      text = text.substring(1);
    }
    if (!text.isBlank()) {
      queryBuilder.append(text);
    }
    return queryBuilder;
  }

  public static Query.Filter filterNumeric(String field, Instant beginAt, Instant endAt) {
    return new Query.NumericFilter(field, beginAt.toEpochMilli(), endAt.toEpochMilli());
  }
}
