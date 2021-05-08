package com.loghub.loggenerator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class LogGeneratorApplication {

	private static String generateRandomString(final int stringLength) {
		Random random = new Random();

		// 97 is for 'a' and 122 for 'z'
		return random.ints(97, 122 + 1)
				.filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
				.limit(stringLength)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
				.toString();
	}

	public static void main(String[] args) {
		SpringApplication.run(LogGeneratorApplication.class, args);

		Logger logger = LoggerFactory.getLogger(LogGeneratorApplication.class);
		MDC.put("businessProperties.test", "test");
		MDC.put("businessProperties.businessValue", "notest");

		while (true) {
			String generatedString = generateRandomString(50);
			logger.info(generatedString);
			try {
				TimeUnit.SECONDS.sleep(5);
			} catch (InterruptedException e) {
				logger.error(generatedString);
			}
		}
	}

}
