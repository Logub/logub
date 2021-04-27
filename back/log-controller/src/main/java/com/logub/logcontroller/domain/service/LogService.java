package com.logub.logcontroller.domain.service;

import com.logub.logcontroller.domain.DomainMapper;
import com.logub.logcontroller.domain.model.LogSearch;
import com.logub.logcontroller.domain.model.LogubLog;
import com.logub.logcontroller.repository.LogRepository;
import com.logub.logcontroller.repository.model.RLogubLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class LogService {

  @Autowired
  private LogRepository logRepository;

  @Autowired
  private DomainMapper mapper;

  public void saveLog(LogubLog log) {

    logRepository.save(mapper.toRepository(log));
  }

  public List<LogubLog> getLogs(LogSearch text) {
    throw new UnsupportedOperationException();
  }
}
