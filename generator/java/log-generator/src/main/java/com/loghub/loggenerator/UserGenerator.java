package com.loghub.loggenerator;

import com.github.javafaker.Faker;
import com.loghub.loggenerator.model.User;
import com.loghub.loggenerator.repository.IUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class UserGenerator {
    Logger logger = LoggerFactory.getLogger(UserGenerator.class);

    @Autowired
    private IUserRepository userRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void initializeFiveUsers() {
        logger.info("Initialization of 5 default users");
        Faker faker = new Faker();

        for (int i = 0; i < 5; ++i) {
            try {
                User user = new User(faker.name().firstName(), faker.name().lastName(), faker.internet().safeEmailAddress());
                userRepository.save(user);
                logger.info("Created user : " + user);
            } catch (Exception e) {
                logger.error("An error occurred while creating user");
            }
        }
    }
}
