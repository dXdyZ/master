package org.another.tacotools.repository;

import org.another.tacotools.model.Ingredient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IngredientRepository extends CrudRepository<Ingredient, String> {
    void deleteIngredientById(String ingredientId);
}
