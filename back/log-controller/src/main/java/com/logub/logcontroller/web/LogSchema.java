package com.logub.logcontroller.web;

import com.logub.logcontroller.api.LogSearchDto;
import com.logub.logcontroller.api.LogubLogDto;
import com.logub.logcontroller.domain.service.LogSchemaService;
import com.logub.logcontroller.domain.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/logs")
public class LogSchema {
  @Autowired
  private LogSchemaService logService;
  @Autowired
  private WebMapper mapper;

  @GetMapping(path = "/schema")
  public List<String> searchLog(){
    return logService.getSchema();
  }
}
