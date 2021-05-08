package com.loghub.loggenerator;

import com.github.javafaker.App;
import com.github.javafaker.Faker;
import com.loghub.loggenerator.model.Quote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.annotation.PostConstruct;

@Service
public class QuoteGeneratorService {
  Logger LOGGER = LoggerFactory.getLogger(QuoteGeneratorService.class);
  List<App> apps;

  public void generate() {
    StringBuilder log = new StringBuilder();
    Faker faker = new Faker();
    Random random = new Random();
    App app = apps.get(random.nextInt(apps.size()));
    final Level[] values = Level.values();
    Quote quote = quoteGenerator(random, faker);
    try (MDC.MDCCloseable res1 = MDC.putCloseable("app", app.name());
         final MDC.MDCCloseable res2 = MDC.putCloseable("origin", quote.getFrom());
         final MDC.MDCCloseable res3 = MDC
             .putCloseable("correlationId", UUID.randomUUID().toString());
         final MDC.MDCCloseable res4 = MDC
             .putCloseable("originRequest", faker.country().name())) {
      Level level = values[random.nextInt(values.length)];
      log.append(quote.getQuote());
      switch (level) {
        case INFO:
          LOGGER.info(log.toString());
          break;
        case WARN:
          LOGGER.warn(log.toString());
          break;
        case DEBUG:
          LOGGER.debug(log.toString());
          break;
        case ERROR:
          LOGGER.error(log.toString());
          break;
        case TRACE:
          LOGGER.trace(log.toString());
          break;
      }
    }
  }

  public Quote quoteGenerator(Random random, Faker faker) {
    switch (random.nextInt(5)) {
      case 0:
        return new Quote("RickAndMorty", faker.rickAndMorty().quote());
      case 1:
        return new Quote("GameOfThrones", faker.gameOfThrones().quote());
      case 2:
        return new Quote("ChuckFact", faker.chuckNorris().fact());
      case 3:
        return new Quote("LoremIpsum", faker.lorem().sentences(1).get(0));
      case 4:
        return new Quote("LeagueOfLegends", faker.leagueOfLegends().quote());
      default:
        return new Quote("Shakespeare", faker.shakespeare().hamletQuote());
    }
  }

  public static enum Level {
    INFO, WARN, ERROR, TRACE, DEBUG
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
