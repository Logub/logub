package com.logub.logcontroller.web;

import com.logub.logcontroller.api.LogSearchDto;
import com.logub.logcontroller.api.LogubLogDto;
import com.logub.logcontroller.api.SystemPropertiesDto;
import com.logub.logcontroller.api.schema.BusinessFieldDto;
import com.logub.logcontroller.api.schema.LogubFieldTypeDto;
import com.logub.logcontroller.api.schema.LogubSortDto;
import com.logub.logcontroller.domain.model.LogSearch;
import com.logub.logcontroller.domain.model.LogubLog;
import com.logub.logcontroller.domain.model.LogubSort;
import com.logub.logcontroller.domain.model.SystemProperties;
import com.logub.logcontroller.domain.model.schema.BusinessField;
import com.logub.logcontroller.domain.model.schema.LogubFieldType;
import org.mapstruct.Mapper;

import java.util.Optional;

@Mapper(componentModel = "spring")
public abstract class WebMapper {
  public abstract LogubLog toDomain(LogubLogDto log);
  public abstract LogSearch toDomain(LogSearchDto log);
  public abstract SystemProperties toDomain(SystemPropertiesDto log);
  public Optional<SystemProperties> toDomain(Optional<SystemPropertiesDto> value){
    return value.map(this::toDomain);
  }
  public abstract LogubSort toDomain(LogubSortDto value);
  public Optional<LogubSort> mapLogubSort(Optional<LogubSortDto> value){
    return value.map(this::toDomain);
  }
  public abstract BusinessField toDomain(BusinessFieldDto field);
  public abstract SystemPropertiesDto toWeb(SystemProperties log);
  public abstract LogubLogDto toWeb(LogubLog log);
  public abstract LogSearchDto toWeb(LogSearch log);
  public Optional<SystemPropertiesDto> toWeb(Optional<SystemProperties> value){
    return value.map(this::toWeb);
  }
  public abstract BusinessFieldDto toWeb(BusinessField field);

  public abstract  LogubSortDto toWeb(LogubSort value);
  public Optional<LogubSortDto> toWebLogubSort(Optional<LogubSort> value){
    return value.map(this::toWeb);
  }
}
