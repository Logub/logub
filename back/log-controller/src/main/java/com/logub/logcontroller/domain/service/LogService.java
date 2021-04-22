package com.logub.logcontroller.domain.service;

import com.logub.logcontroller.domain.DomainMapper;
import com.logub.logcontroller.domain.model.LogubLog;
import com.logub.logcontroller.repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LogService {
  @Autowired
  private LogRepository logRepository;

  @Autowired
  private DomainMapper mapper;

  public void saveLog(LogubLog log){
    logRepository.save(mapper.toRepository(log));
  }

  public List<LogubLog> getLogs(String text) {
    return logRepository.getLogs(text).stream().map(v -> mapper.toDomain(v)).collect(Collectors.toList());
  }
}
