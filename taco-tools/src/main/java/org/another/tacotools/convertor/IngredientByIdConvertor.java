package org.another.tacotools.convertor;

import org.another.tacotools.repository.IngredientRepository;
import org.springframework.core.convert.converter.Converter;
import org.another.tacotools.model.Ingredient;
import org.springframework.stereotype.Component;
import org.another.tacotools.model.Ingredient.Type;

import java.util.HashMap;
import java.util.Map;

@Component
public class IngredientByIdConvertor implements Converter<String, Ingredient> {
    private IngredientRepository ingredientRepository;
    @Override
    public Ingredient convert(String id) {
        return ingredientRepository.findById(id).orElse(null);
    }

    public IngredientByIdConvertor(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }
}
