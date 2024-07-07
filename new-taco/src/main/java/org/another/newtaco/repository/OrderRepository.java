package org.another.newtaco.repository;

import org.another.newtaco.entity.TacoOrder;
import org.springframework.data.repository.CrudRepository;


public interface OrderRepository extends CrudRepository<TacoOrder, Long> {
}
