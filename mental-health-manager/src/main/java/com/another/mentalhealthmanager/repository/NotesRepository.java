package com.another.mentalhealthmanager.repository;

import com.another.mentalhealthmanager.entity.Notes;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotesRepository extends CrudRepository<Notes, Long> {
    Iterable<Notes> findAllByUsersId(Long id);
}
