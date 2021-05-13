package com.loghub.loggenerator.controller;

import com.loghub.loggenerator.business.IUserBusiness;
import com.loghub.loggenerator.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {
    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    private final IUserBusiness userBusiness;

    private final String ERROR_MESSAGE = "An error occurred while processing your request : ";

    @Autowired
    public UserController(IUserBusiness userBusiness) {
        this.userBusiness = userBusiness;
    }

    @GetMapping("")
    public ResponseEntity getUsers() {
        try {
            logger.info("GET /users");
            return ResponseEntity.ok(userBusiness.retrieveAll());
        } catch (Exception e) {
            logger.error(ERROR_MESSAGE + "GET /users");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity postUser(@RequestBody User user) {
        logger.info("POST /users");
        try (var res = MDC.putCloseable("firstName", user.getFirstName());
             var res2 = MDC.putCloseable("lastName", user.getLastName());
             var res3 = MDC.putCloseable("email", user.getEmail())){
            logger.info("User to create : " + user.toString());
            return ResponseEntity.ok(userBusiness.createUser(user));
        } catch (IllegalArgumentException e) {
            logger.error(ERROR_MESSAGE + "POST /users");
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity deleteUser(@PathVariable(value = "id") final Long id) {
        try (var res = MDC.putCloseable("userId", Long.toString(id))){
            logger.info("DELETED /users");
            logger.info("User id to delete : " + id);
            return ResponseEntity.ok(userBusiness.deleteUser(id));
        } catch (IllegalArgumentException e) {
            logger.error(ERROR_MESSAGE + "DELETE /users");
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }
    }
}
