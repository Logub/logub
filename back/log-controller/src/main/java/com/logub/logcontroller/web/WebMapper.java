package com.logub.logcontroller.web;

import com.logub.logcontroller.api.LogDto;
import com.logub.logcontroller.domain.model.LogubLog;
import com.logub.logcontroller.repository.model.RedisLog;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class WebMapper {
  public abstract LogubLog toDomain(LogDto log);
  public abstract LogDto toWeb(LogubLog log);
}
