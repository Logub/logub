package com.logub.logcontroller.domain.service;

import com.logub.logcontroller.domain.DomainMapper;
import com.logub.logcontroller.domain.model.LogubLog;
import com.logub.logcontroller.repository.LogRepository;
import com.redislabs.lettusearch.Field;
import com.redislabs.lettusearch.StatefulRediSearchConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

@Service
public class LogService {

  @Autowired
  private StatefulRediSearchConnection<String, String> connection;
    @PostConstruct
  public void testSearch() {
    connection.sync().create("log", new Field.Text<>("event.message.value"),new Field.Text<>("event.service"), new Field.Text<>("event.timestamp"), new Field.Text<>("event.level") );
  }
  @Autowired
  private LogRepository logRepository;

  @Autowired
  private DomainMapper mapper;

  public void saveLog(LogubLog log){
    logRepository.save(mapper.toRepository(log));
  }

  public List<LogubLog> getLogs(String text) {
    List<LogubLog> logubLog = new ArrayList<>();
    logRepository.findAll().iterator().forEachRemaining(v -> logubLog.add(mapper.toDomain(v)));

    return logubLog;
  }
}
