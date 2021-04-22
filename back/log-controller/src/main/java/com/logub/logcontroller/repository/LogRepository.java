package com.logub.logcontroller.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.logub.logcontroller.repository.model.RLogubLog;
import io.redisearch.Query;
import io.redisearch.Schema;
import io.redisearch.client.Client;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.exceptions.JedisDataException;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

@Repository
@Slf4j
public class LogRepository {
  @Autowired
  private Client redisClient;
  @Autowired
  private ObjectMapper objectMapper;

  public void save(RLogubLog log) {
    Map<String, Object> fields = objectMapper.convertValue(log, HashMap.class);
// RediSearch 2.0+ supports working with Redis Hash commands
    redisClient.addDocument(log.getId(), fields);

  }

  @PostConstruct
  public void createInDB() {
    Schema sc = new Schema()
        .addTextField("id", 1)
        .addSortableNumericField("timestamp")
        .addTextField("properties", 1)
        .addTextField("content", 1);

    try {
      Map<String, Object> info = redisClient.getInfo();
    } catch (JedisDataException jedisDataException) {
      redisClient.createIndex(sc, Client.IndexOptions.defaultOptions());
    }
  }

  public List<RLogubLog> getLogs(String text) {
    return redisClient.search(new Query(text)).docs.stream().map(v -> {
      try {
        return objectMapper.readValue(v.getPayload(), RLogubLog.class);
      } catch (IOException e) {
        log.error("an error occurs", e);
        throw new IllegalArgumentException();
      }
    }).collect(Collectors.toList());
  }
}
