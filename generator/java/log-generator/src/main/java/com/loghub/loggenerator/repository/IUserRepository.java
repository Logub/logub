package com.loghub.loggenerator.repository;

import com.loghub.loggenerator.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUserRepository extends CrudRepository<User, Long> {
    List<User> findByIdLike(Long id);
    List<User> findAll();
}
