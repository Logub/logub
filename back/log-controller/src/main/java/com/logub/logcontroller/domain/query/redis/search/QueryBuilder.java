package com.logub.logcontroller.domain.query.redis.search;

import lombok.Data;

import java.util.Arrays;
import java.util.List;

@Data
public class QueryBuilder {
  private StringBuilder query;
  private List<String> toReplace;

  public QueryBuilder() {
    this.query = new StringBuilder();
    toReplace = Arrays.asList(".", "-");
  }

  public QueryBuilder append(QueryBuilder query) {

    String queryString = '('+query.getQuery().toString()+')';
    if(this.query.length() == 0){
      return this.append(queryString);
    }
    return this.append(" ").append(queryString);
  }

  public QueryBuilder append(String query) {
    this.query.append(query);
    return this;
  }

  public QueryBuilder append(char query) {
    this.query.append(query);
    return this;
  }

  public String toRedisQuery() {
    String finalQuery = this.query.toString();
    for (String str : toReplace) {
      finalQuery = finalQuery.replace(str, "\\" + str);
    }
    finalQuery = finalQuery.replace("\\-@", "-@");
    return finalQuery;
  }
  public boolean isBlank(){
    return this.query.toString().isBlank();
  }


  public boolean isEmpty(){
    return this.query.toString().isEmpty();
  }
}
