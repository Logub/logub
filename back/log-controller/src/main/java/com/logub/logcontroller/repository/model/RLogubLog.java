package com.logub.logcontroller.repository.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@RedisHash("log")
public class RLogubLog implements Serializable {
  @Builder.Default
  @Id
  private String id = UUID.randomUUID().toString();

  private String index = "principal";

  private RLogEvent event;
}
