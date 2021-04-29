package com.logub.logcontroller.domain.service;

import com.logub.logcontroller.domain.model.schema.BusinessField;
import com.logub.logcontroller.repository.LogSchemaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class LogSchemaService {
  @Autowired
  private LogSchemaRepository logSchemaRepository;

  public List<String> getSchema() {
    return logSchemaRepository.getSchema();
  }

  public void indexField(BusinessField field) {
    String fieldName = "event.businessProperties." + field.getName();
    logSchemaRepository.indexField(fieldName, field.getType().toRedisSearchType());
  }
}
