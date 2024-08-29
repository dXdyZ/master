package com.example.reactivenewtaco.non_relationalBD.mongoDB.repository;

import com.example.reactivenewtaco.non_relationalBD.mongoDB.entity.Ingredient;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

@Repository
@CrossOrigin(origins = "http://localhost:8080")
public interface IngredientRepository extends ReactiveCrudRepository<Ingredient, String> {
}
