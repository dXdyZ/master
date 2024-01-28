package org.another.spingrestapi.repository;

import org.another.spingrestapi.model.Coffee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoffeeRepository extends CrudRepository<Coffee, String> {
}
