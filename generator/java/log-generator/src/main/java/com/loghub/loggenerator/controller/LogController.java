package com.loghub.loggenerator.controller;

import com.loghub.loggenerator.dto.LogubLogDto;
import com.loghub.loggenerator.service.LoggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Log controller to publish custom logs.
 */
@RestController
@RequestMapping("/logs")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class LogController {

    /**
     * The logger service.
     */
    @Autowired
    private LoggerService loggerService;

    /**
     * Post a log.
     *
     * @param logDto
     * the log info
     */
    @PostMapping
    public void logs(@RequestBody final LogubLogDto logDto){
        this.loggerService.log(logDto.getLevel(), logDto.getMessage(), logDto.getBusinessProperties());
    }

}
