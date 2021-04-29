package com.logub.logcontroller.repository;

import com.google.common.collect.Streams;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.logub.logcontroller.domain.model.LogSearch;
import com.logub.logcontroller.domain.model.LogubSort;
import com.logub.logcontroller.repository.model.RLogubLog;
import io.redisearch.Document;
import io.redisearch.Query;
import io.redisearch.client.Client;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.hash.Jackson2HashMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

@Repository
@Slf4j
public class LogRepository {
  private final String INDEX_NAME = "log";

  @Value("${logub.redis.client}")
  private String adresse;
  @Value("${logub.redis.port}")
  private int port;
  @Autowired
  private Jackson2HashMapper jackson2HashMapper;
  @Autowired
  private ObjectMapper objectMapper;

  private Client redisSearchClient;

  public void save(RLogubLog log) {

    redisSearchClient.addDocument("log:" + log.getId(), jackson2HashMapper.toHash(log));
  }

  /**
   * Search list.
   *
   * @param logSearch the log search
   * @return the list
   */
  public List<RLogubLog> search(LogSearch logSearch) {
    String queryString = logSearch.toQuery();
    if(queryString.isBlank()){
      queryString = "*";
    }
    Query query = new Query(queryString)
        .limit(logSearch.getOffset(), logSearch.getLimit());
    if (logSearch.getSort().isPresent()) {
      query.setSortBy(logSearch.getSort().get().getField(),
          logSearch.getSort().get().getOrder().equals(
              LogubSort.LogubOrder.ASC));
    } else {
      query.setSortBy("event.timestamp", false);
    }
    List<Document> docs = redisSearchClient
        .search(query).docs;
    return docs.stream().map(v -> Streams.stream(v.getProperties())
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)))
        .map(v -> objectMapper.convertValue(jackson2HashMapper.fromHash(v), RLogubLog.class))
        .collect(
            Collectors.toList());
  }

  @PostConstruct
  public void createConnector() {

    try {
      redisSearchClient = new Client(INDEX_NAME, adresse, port);
    } catch (Exception exception) {
      log.warn("createConnector", exception);
    }
  }

}
