package com.example.authbookserve.repo;

import com.example.authbookserve.entity.User;
import jakarta.persistence.Lob;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    UserDetails findByUsername(String username);
}
