package org.another.newtaco.repository;


import org.another.newtaco.entity.Ingredient;
import org.springframework.data.repository.CrudRepository;


import java.util.Optional;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {
}
