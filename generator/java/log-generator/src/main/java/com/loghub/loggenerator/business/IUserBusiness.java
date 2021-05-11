package com.loghub.loggenerator.business;

import com.loghub.loggenerator.model.User;

import java.util.List;

public interface IUserBusiness {

    /**
     * Retrieve all users
     *
     * @return a list of users in json
     */
    List<User> retrieveAll();

    /**
     * Create a user in database
     *
     * @param user data
     * @return the created user
     */
    User createUser(User user);

    /**
     * Delete a user in database
     *
     * @param id of the user to delete
     * @return true if deleted, else false
     */
    boolean deleteUser(long id);
}
