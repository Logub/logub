package com.logub.logcontroller.web;

import com.logub.logcontroller.api.schema.BusinessFieldDto;
import com.logub.logcontroller.domain.service.LogSchemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/logs/schema")
@CrossOrigin(origins = "*")
public class LogSchemaController {
  @Autowired
  private LogSchemaService logService;
  @Autowired
  private WebMapper mapper;

  /**
   * Search log list.
   *
   * @return the list
   */
  @GetMapping
  public List<String> getSchema(){
    return logService.getSchema();
  }
  @PostMapping
  public void addField(@RequestBody BusinessFieldDto field){
    logService.indexField(mapper.toDomain(field));
  }
}
