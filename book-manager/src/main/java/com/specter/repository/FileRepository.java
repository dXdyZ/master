package com.specter.repository;

import com.specter.entity.FileBooks;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileRepository extends CrudRepository<FileBooks, Long> {
    void deleteByBooksId(Long id);
    Optional<FileBooks> findByBooksId(Long id);
}
