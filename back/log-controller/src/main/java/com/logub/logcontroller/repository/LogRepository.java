package com.logub.logcontroller.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.logub.logcontroller.repository.model.RedisLog;
import io.redisearch.Schema;
import io.redisearch.client.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.exceptions.JedisDataException;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.PostConstruct;

@Repository
public class LogRepository {
  @Autowired
  private Client redisClient;
  @Autowired
  private ObjectMapper objectMapper;

  public void save(RedisLog log) {
    Map<String, Object> fields = objectMapper.convertValue(log, HashMap.class);
// RediSearch 2.0+ supports working with Redis Hash commands
    redisClient.addDocument(UUID.randomUUID().toString(), fields);

  }

  @PostConstruct
  public void createInDB() {
    Schema sc = new Schema()
        .addSortableNumericField("timestamp")
        .addTextField("content", 1);

    try {
      Map<String, Object> info = redisClient.getInfo();
    } catch (JedisDataException jedisDataException) {
      redisClient.createIndex(sc, Client.IndexOptions.defaultOptions());
    }
  }
}
