package org.another.tascman.repository;

import org.another.tascman.model.AnderTask;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnderTaskRepository extends CrudRepository<AnderTask, Long> {
}
