package com.logub.logcontroller.repository;

import com.logub.logcontroller.repository.model.RLogubLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepository extends CrudRepository<RLogubLog, String> {
}
