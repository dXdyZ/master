package org.another.newtaco.repository;

import org.another.newtaco.entity.Taco;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface TacoRepository extends CrudRepository<Taco, Long> {

}
