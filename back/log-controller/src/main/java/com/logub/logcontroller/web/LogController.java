package com.logub.logcontroller.web;

import com.logub.logcontroller.api.search.LogSearchDto;
import com.logub.logcontroller.api.LogubLogDto;
import com.logub.logcontroller.domain.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/logs")
@CrossOrigin(origins = "*")
public class LogController {

  @Autowired
  private LogService logService;

  @Autowired
  private WebMapper mapper;

  @PostMapping
  public void logs(@RequestBody LogubLogDto logDto){
    logService.saveLog(mapper.toDomain(logDto));
  }

  @PostMapping(path = "/search")
  public List<LogubLogDto> searchLog(@RequestBody LogSearchDto logDto){
    return logService.searchLog(mapper.toDomain(logDto)).stream().map(v -> mapper.toWeb(v)).collect(
        Collectors.toList());
  }
}
