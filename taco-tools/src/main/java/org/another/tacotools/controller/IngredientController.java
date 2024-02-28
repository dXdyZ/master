package org.another.tacotools.controller;

import org.another.tacotools.model.Ingredient;
import org.another.tacotools.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/ingredients", produces = "application/json")
@CrossOrigin(origins = "http://localhost:8080")
public class IngredientController {
    private IngredientRepository repository;

    @Autowired
    public IngredientController(IngredientRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    @PreAuthorize("#{hasRole('ADMIN')}")
    @ResponseStatus(HttpStatus.CREATED)
    public Ingredient saveIngredient(@RequestBody Ingredient ingredient) {
        return repository.save(ingredient);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("#{hasRole('ADMIN')}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteIngredient(@PathVariable("id") String ingredientId) {
        repository.deleteIngredientById(ingredientId);
    }

    @GetMapping
    public Iterable<Ingredient> allIngredients() {
        return repository.findAll();
    }
}

