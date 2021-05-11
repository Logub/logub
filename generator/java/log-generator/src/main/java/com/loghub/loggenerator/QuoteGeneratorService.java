package com.loghub.loggenerator;

import com.github.javafaker.App;
import com.github.javafaker.Faker;
import com.loghub.loggenerator.model.LogLevelEnum;
import com.loghub.loggenerator.model.Quote;
import com.loghub.loggenerator.service.LoggerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import javax.annotation.PostConstruct;

@Service
public class QuoteGeneratorService {
  Logger LOGGER = LoggerFactory.getLogger(QuoteGeneratorService.class);
  List<App> apps;


  @Autowired
  private LoggerService loggerService;

  public void generate() {
    StringBuilder log = new StringBuilder();
    Faker faker = new Faker();
    Random random = new Random();
    App app = apps.get(random.nextInt(apps.size()));
    final LogLevelEnum[] values = LogLevelEnum.values();
    Quote quote = quoteGenerator(random, faker);
    LogLevelEnum level = values[random.nextInt(values.length)];
    log.append(quote.getQuote());

    final Map<String, String> businessProperties = new HashMap<>();
    businessProperties.put("app", app.name());
    businessProperties.put("origin", quote.getFrom());
    businessProperties.put("correlationId", UUID.randomUUID().toString());
    businessProperties.put("originRequest", faker.country().name());
    this.loggerService.log(level, log.toString(), businessProperties);
  }

    public Quote quoteGenerator(Random random, Faker faker) {
    switch (random.nextInt(5)) {
      case 0:
        return new Quote("Yoda", faker.yoda().quote());
      case 1:
        return new Quote("GameOfThrones", faker.gameOfThrones().quote());
      case 2:
        return new Quote("ChuckFact", faker.chuckNorris().fact());
      case 3:
        return new Quote("LoremIpsum", faker.lorem().sentences(1).get(0));
      case 4:
        return new Quote("HarryPotter", faker.harryPotter().quote());
      default:
        return new Quote("Shakespeare", faker.shakespeare().hamletQuote());
    }
  }


  @PostConstruct
  public void init() {
    apps = new ArrayList<>();
    Faker faker = new Faker();
    for (int i = 0; i < 5; i++) {
      apps.add(faker.app());
    }
  }
}
