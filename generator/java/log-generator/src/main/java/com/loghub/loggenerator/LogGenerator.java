package com.loghub.loggenerator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Component
public class LogGenerator {
    Logger logger = LoggerFactory.getLogger(LogGenerator.class);
    @Autowired
    private QuoteGeneratorService quoteGeneratorService;

    @Scheduled(fixedDelay = 1)
    public void generateLog() {
        quoteGeneratorService.generate();

        Random random = new Random();
        try {
            TimeUnit.MILLISECONDS.sleep(random.nextInt(1500));
        } catch (InterruptedException e) {
            logger.error("An error occurred while generating a log");
        }
    }

    private String generateRandomString(final int stringLength) {
        MDC.put("test", "test");
        MDC.put("businessValue", "test");
        String generatedString = generateRandomString(50);
        logger.info(generatedString);

        Random random = new Random();

        // 97 is for 'a' and 122 for 'z'
        return random.ints(97, 122 + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(stringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
