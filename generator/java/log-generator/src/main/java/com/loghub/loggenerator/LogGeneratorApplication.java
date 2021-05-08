package com.loghub.loggenerator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
@EnableScheduling
public class LogGeneratorApplication {


  public static void main(String[] args) {
    SpringApplication.run(LogGeneratorApplication.class, args);

  }

}
