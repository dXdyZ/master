package com.specter.repository;

import com.specter.entity.Role;
import com.specter.entity.Users;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<Users, Long> {
    Optional<Users> findByUsername(String username);
    void deleteByUsername(String username);

    @Modifying
    @Transactional
    @Query("UPDATE Users u SET u.role = :role WHERE u.username = :username")
    void updateRoleByUsername(@Param("username") String username,
                             @Param("role") Role role);
}
