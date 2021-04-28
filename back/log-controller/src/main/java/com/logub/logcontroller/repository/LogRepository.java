package com.logub.logcontroller.repository;

import com.google.common.collect.Streams;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.logub.logcontroller.domain.model.LogSearch;
import com.logub.logcontroller.repository.model.RLogubLog;
import io.redisearch.Document;
import io.redisearch.Query;
import io.redisearch.Schema;
import io.redisearch.client.Client;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.hash.Jackson2HashMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
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
  @Autowired
  private LogSchemaRepository logSchemaRepository;

  private Client redisSearchClient;

  public void save(RLogubLog log) {

    redisSearchClient.addDocument("log:" + log.getId(), jackson2HashMapper.toHash(log));
  }

  public List<RLogubLog> search(LogSearch logSearch) {

    List<Document> docs = redisSearchClient
        .search(
            new Query(logSearch.toTagQuery())
                .limit(logSearch.getOffset(), logSearch.getLimit())).docs;
    return docs.stream().map(v -> Streams.stream(v.getProperties())
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)))
        .map(v -> objectMapper.convertValue(jackson2HashMapper.fromHash(v), RLogubLog.class))
        .collect(
            Collectors.toList());
  }

  @PostConstruct
  public void createClientAndSchema() {

    try {
      redisSearchClient = new Client(INDEX_NAME, adresse, port);
      Schema schema = createSchema();
      redisSearchClient.createIndex(schema, Client.IndexOptions.defaultOptions());
    } catch (Exception exception) {
      log.warn("createClientAndSchema", exception);
      ArrayList<ArrayList<byte[]>> info =
          ((ArrayList<ArrayList<byte[]>>) redisSearchClient.getInfo().get("fields"));
      for (ArrayList<byte[]> bytes : info) {
        List<String> schema = logSchemaRepository.getSchema();
        String fieldName = new String(bytes.get(0));
        if (schema.stream().anyMatch(v -> v.equals(fieldName))) {
          log.info("value already present in schema {}", fieldName);
          continue;
        }
        if (fieldName.equals("event.message")) {
          redisSearchClient
              .alterIndex(new Schema.Field(fieldName, Schema.FieldType.FullText, true));
        } else {
          redisSearchClient.alterIndex(new Schema.Field(fieldName, Schema.FieldType.Tag, true));
        }
      }
    }
  }

  private Schema createSchema() {
    Schema schema = new Schema();
    for (String field : logSchemaRepository.getSchema()) {
      if (field.equals("event.message")) {
        schema.addField(new Schema.Field(field, Schema.FieldType.FullText, true));
      } else if (field.equals("event.timestamp")) {
        schema.addField(new Schema.Field(field, Schema.FieldType.Numeric, true));
      } else {
        schema.addField(new Schema.Field(field, Schema.FieldType.Tag, true));
      }
    }
    return schema;
  }

}
