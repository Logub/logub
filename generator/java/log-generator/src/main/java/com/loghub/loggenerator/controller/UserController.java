package com.loghub.loggenerator.controller;

import com.loghub.loggenerator.business.IUserBusiness;
import com.loghub.loggenerator.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    private final IUserBusiness userBusiness;

    @Autowired
    public UserController(IUserBusiness userBusiness) {
        this.userBusiness = userBusiness;
    }

    @GetMapping("")
    public ResponseEntity<List<User>> getUsers() {
        logger.info("GET /users");
        return ResponseEntity.ok(userBusiness.retrieveAll());
    }

    @PostMapping
    public ResponseEntity postUser(@RequestBody User user) {
        logger.info("POST /users");
        try {
            logger.info("User to create : " + user.toString());
            return ResponseEntity.ok(userBusiness.createUser(user));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }
    }
}
