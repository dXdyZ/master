package com.example.jwtserver.repositories;

import com.example.jwtserver.enity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends CrudRepository<User, Long> {
     Optional<User> findByUsername(String username);
}
