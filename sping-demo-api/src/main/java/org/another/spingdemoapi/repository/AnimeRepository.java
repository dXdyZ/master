package org.another.spingdemoapi.repository;

import org.another.spingdemoapi.model.Anime;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnimeRepository extends CrudRepository<Anime, Long> {
}
