package com.logub.logcontroller.repository;

import com.logub.logcontroller.domain.model.LogLevel;
import com.logub.logcontroller.repository.model.RLogEvent;
import com.logub.logcontroller.repository.model.RLogubLog;
import com.logub.logcontroller.repository.model.RSystemProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.hash.Jackson2HashMapper;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

@Repository
@Slf4j
public class LogSchemaRepository {
  private final String INDEX_NAME = "schema";

  @Autowired
  private Jackson2HashMapper jackson2HashMapper;

  @Autowired
  private RedisTemplate<String, String> redisTemplate;

  public List<String> getSchema() {
    return redisTemplate.opsForList().range(INDEX_NAME + ":1", 0, -1);
  }

  @PostConstruct
  public void createSchema() {
    String h = INDEX_NAME + ":1";
    if (!redisTemplate.hasKey(h)) {
      Map<String, Object> fields =
          jackson2HashMapper.toHash(RLogubLog.builder().event(RLogEvent
              .builder()
              .level(LogLevel.ERROR)
              .message("nothing")
              .service("nothing")
              .timestamp(Instant.now())
              .tags(RSystemProperties.builder()
                  .containerName("nothing")
                  .env("env")
                  .host("host")
                  .imageName("imagename")
                  .build())
              .build()).build());
      redisTemplate.opsForList().rightPushAll(h, fields.keySet());
    }
  }

}
