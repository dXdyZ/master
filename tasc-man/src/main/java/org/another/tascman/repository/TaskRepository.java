package org.another.tascman.repository;

import org.another.tascman.model.TaskName;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends CrudRepository<TaskName, Long> {
}
