package com.logub.logcontroller.repository;

import com.logub.logcontroller.domain.model.LogLevel;
import com.logub.logcontroller.repository.model.RLogubLog;
import com.logub.logcontroller.repository.model.RSystemProperties;
import io.redisearch.Schema;
import io.redisearch.client.Client;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.hash.Jackson2HashMapper;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

@Repository
@Slf4j
public class LogSchemaRepository {
  private final String INDEX_SCHEMA = "schema";
  private final String INDEX_LOG = "log";
  @Value("${logub.redis.client}")
  private String adresse;
  @Value("${logub.redis.port}")
  private int port;
  @Autowired
  private Jackson2HashMapper jackson2HashMapper;

  @Autowired
  private RedisTemplate<String, String> redisTemplate;
  private Client redisSearchClient;

  public List<String> getSchema() {
    return redisTemplate.opsForList().range(INDEX_SCHEMA + ":1", 0, -1);
  }

  @PostConstruct
  public void initSchema() {
    String h = INDEX_SCHEMA + ":1";
    List<String> schemeSet = redisTemplate.hasKey(h) ? getSchema() : Collections.emptyList();
    Map<String, Object> fields =
        jackson2HashMapper.toHash(RLogubLog.builder()
            .level(LogLevel.ERROR)
            .message("nothing")
            .timestamp(Instant.now().toEpochMilli())
            .systemProperties(RSystemProperties.builder()
                .containerName("nothing")
                .env("env")
                .host("host")
                .imageName("imagename")
                .logger("logger")
                .thread("thread")
                .service("service")
                .build())
            .build());
    Set<String> systemField = fields.keySet();
    schemeSet.forEach(systemField::remove);
    log.info("new fields : {}", systemField);
    if (!systemField.isEmpty()) {
      redisTemplate.opsForList()
          .rightPushAll(h, new ArrayList<>(systemField));
    }
    try {
      redisSearchClient = new Client(INDEX_LOG, adresse, port);
      Schema schema = createSchema();
      redisSearchClient.createIndex(schema, Client.IndexOptions.defaultOptions());
    } catch (Exception exception) {
      log.warn("createClientAndSchema", exception);
      ArrayList<ArrayList<byte[]>> info =
          ((ArrayList<ArrayList<byte[]>>) redisSearchClient.getInfo().get("fields"));
      for (ArrayList<byte[]> bytes : info) {
        List<String> schema = this.getSchema();
        String fieldName = new String(bytes.get(0));
        if (schema.stream().anyMatch(v -> v.equals(fieldName))) {
          log.info("value already present in schema {}", fieldName);
          continue;
        }
        if (fieldName.equals("message")) {
          redisSearchClient
              .alterIndex(new Schema.Field(fieldName, Schema.FieldType.FullText, true));
        } else {
          log.info("new global properties detected {}", fieldName);
          redisSearchClient.alterIndex(new Schema.Field(fieldName, Schema.FieldType.Tag, true));
        }
      }
    }
  }

  public void indexField(String fieldName, Schema.FieldType fieldType) {
    redisSearchClient.alterIndex(new Schema.Field(fieldName, fieldType, true));
    redisTemplate.opsForList().rightPush(INDEX_SCHEMA + ":1", fieldName);

  }


  private Schema createSchema() {
    Schema schema = new Schema();
    for (String field : this.getSchema()) {
      if (field.equals("message")) {
        schema.addField(new Schema.Field(field, Schema.FieldType.FullText, true));
      } else if (field.equals("timestamp")) {
        schema.addField(new Schema.Field(field, Schema.FieldType.Numeric, true));
      } else {
        schema.addField(new Schema.Field(field, Schema.FieldType.Tag, true));
      }
    }
    return schema;
  }
}
