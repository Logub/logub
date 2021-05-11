package com.loghub.loggenerator.service;

import com.loghub.loggenerator.QuoteGeneratorService;
import com.loghub.loggenerator.model.LogLevelEnum;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

/**
 * Service to log with specific properties.
 */
@Service
public class LoggerService {

    /**
     * Logger.
     */
    private static Logger LOGGER = LoggerFactory.getLogger(LoggerService.class);

    /**
     * Log the given informations using MDC.
     *
     * @param level
     * log level
     * @param logMessage
     * log message
     * @param businessProperties
     * business properties to add
     */
    public void log(final LogLevelEnum level, final String logMessage, final Map<String, String> businessProperties) {
        businessProperties.entrySet().stream().forEach(entry -> MDC.putCloseable(entry.getKey(), entry.getValue()));
        switch (level) {
            case INFO:
                LOGGER.info(logMessage);
                break;
            case WARN:
                LOGGER.warn(logMessage);
                break;
            case DEBUG:
                LOGGER.debug(logMessage);
                break;
            case ERROR:
                LOGGER.error(logMessage);
                break;
            case TRACE:
                LOGGER.trace(logMessage);
                break;
        }
    }
}
