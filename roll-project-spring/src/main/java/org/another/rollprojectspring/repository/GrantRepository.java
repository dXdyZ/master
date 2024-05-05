package org.another.rollprojectspring.repository;

import org.another.rollprojectspring.model.GrantSystem;
import org.another.rollprojectspring.model.Project;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GrantRepository extends CrudRepository<Project, Long> {
}
