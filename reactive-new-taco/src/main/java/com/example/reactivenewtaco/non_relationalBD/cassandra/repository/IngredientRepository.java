package com.example.reactivenewtaco.non_relationalBD.cassandra.repository;

import com.example.reactivenewtaco.non_relationalBD.cassandra.entity.Ingredient;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientRepository extends ReactiveCrudRepository<Ingredient, String> {
}
