package org.another.rollprojectspring.repository;

import org.another.rollprojectspring.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends CrudRepository<User, Long>{
}
