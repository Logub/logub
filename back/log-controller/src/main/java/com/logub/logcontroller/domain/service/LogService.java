package com.logub.logcontroller.domain.service;

import com.logub.logcontroller.domain.DomainMapper;
import com.logub.logcontroller.domain.model.LogubLog;
import com.logub.logcontroller.repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogService {
  @Autowired
  private LogRepository logRepository;

  @Autowired
  private DomainMapper mapper;

  public void saveLog(LogubLog log){
    logRepository.save(mapper.toRepository(log));
  }
}
