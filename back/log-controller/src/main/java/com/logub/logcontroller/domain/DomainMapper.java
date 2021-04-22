package com.logub.logcontroller.domain;

import com.logub.logcontroller.domain.model.LogubLog;
import com.logub.logcontroller.repository.model.RLogubLog;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class DomainMapper{
  public abstract RLogubLog toRepository(LogubLog log);
  public abstract LogubLog toDomain(RLogubLog log);
}
