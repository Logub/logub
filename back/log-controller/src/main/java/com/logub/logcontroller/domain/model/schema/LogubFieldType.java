package com.logub.logcontroller.domain.model.schema;

import io.redisearch.Schema;

public enum LogubFieldType {
  Tag,
  FullText,
  Geo,
  Numeric;


  public Schema.FieldType toRedisSearchType() {
    switch (this){
      case Geo:
        return Schema.FieldType.Geo;
      case Tag:
        return Schema.FieldType.Tag;
      case Numeric:
        return Schema.FieldType.Numeric;
      case FullText:
        return Schema.FieldType.FullText;
      default:
        throw new IllegalArgumentException();
    }
  }
}
