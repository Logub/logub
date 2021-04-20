package com.logub.logcontroller.config;

import io.redisearch.client.Client;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(Client.class)
public class RedisConfig {
  @Value("${logub.redis.client}")
  private String adresse;
  @Value("${logub.redis.port}")
  private int port;
  @Bean
  public Client redisClient(){
    return new Client("loghub", adresse, port);
  }
}
