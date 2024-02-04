package org.another.springjpapersistenceapi.repository;

import org.another.springjpapersistenceapi.model.Aircraft;
import org.springframework.data.repository.CrudRepository;

public interface AircraftRepository extends CrudRepository<Aircraft, Long> {
}
