package com.specter.repository;

import com.specter.entity.Books;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BooksRepository extends CrudRepository<Books, Long> {
    Optional<Books> findByName(String name);
    Iterable<Books> findByUserUsername(String username);
    List<Books> findByUserUsernameOrderById(String username);
    void deleteByName(String name);
    List<Books> findAllByName(String name);
    boolean deleteAllByUserUsername(String username);
}
