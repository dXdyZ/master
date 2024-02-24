package org.another.tacotools.repository;

import org.another.tacotools.model.Taco;
import org.another.tacotools.model.TacoOrder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TacoRepository extends CrudRepository<Taco, Long> {

    @Query("SELECT t from Taco t")
    Iterable<Taco> findAllTaco(PageRequest page);

}
