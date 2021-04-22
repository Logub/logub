package com.logub.logcontroller.web;

import com.logub.logcontroller.api.LogubLogDto;
import com.logub.logcontroller.domain.model.LogubLog;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class WebMapper {
  public abstract LogubLog toDomain(LogubLogDto log);
  public abstract LogubLogDto toWeb(LogubLog log);
}
