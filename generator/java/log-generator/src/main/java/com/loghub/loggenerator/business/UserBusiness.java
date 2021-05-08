package com.loghub.loggenerator.business;

import com.loghub.loggenerator.model.User;
import com.loghub.loggenerator.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserBusiness implements IUserBusiness {

    @Autowired
    private IUserRepository userRepository;

    @Override
    public List<User> retrieveAll() {
        return this.userRepository.findAll();
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }
}
