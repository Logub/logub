package com.logub.logcontroller.domain;

import com.logub.logcontroller.domain.model.LogubLog;
import com.logub.logcontroller.domain.model.LogubSchema;
import com.logub.logcontroller.repository.model.RLogubLog;
import com.logub.logcontroller.repository.model.schema.RLogubSchema;
import org.mapstruct.Mapper;

import java.time.Instant;
import java.util.Optional;

@Mapper(componentModel = "spring")
public abstract class DomainMapper{
  public abstract RLogubLog toRepository(LogubLog log);
  public abstract RLogubSchema toRepository(LogubSchema log);
  public abstract LogubLog toDomain(RLogubLog log);
  public abstract LogubSchema toDomain(RLogubSchema log);
  public String map(Optional<String> string){
    return string.orElse(null);
  }
  public long map(Instant now){
    return now.getEpochSecond();
  }

  public Instant map(long now){
    return Instant.ofEpochMilli(now);
  }

  public Optional<String> map(String string){
    return Optional.ofNullable(string);
  }
}
