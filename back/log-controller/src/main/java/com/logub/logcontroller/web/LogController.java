package com.logub.logcontroller.web;

import com.logub.logcontroller.api.LogDto;
import com.logub.logcontroller.domain.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/logs")
public class LogController {
  @Autowired
  private LogService logService;
  @Autowired
  private WebMapper mapper;
  @PostMapping
  public void logs(@RequestBody LogDto logDto){
    logService.saveLog(mapper.toDomain(logDto));
  }
}
