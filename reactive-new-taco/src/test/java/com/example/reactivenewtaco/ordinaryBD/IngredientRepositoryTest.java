package com.example.reactivenewtaco.ordinaryBD;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.reactivenewtaco.ordinaryBD.entity.Ingredient;
import com.example.reactivenewtaco.ordinaryBD.repository.IngredientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.ArrayList;

@DataR2dbcTest
public class IngredientRepositoryTest {
    @Autowired
    IngredientRepository ingredientRepository;

    @BeforeEach
    public void setup() {
        Flux<Ingredient> deleteAndInsert = ingredientRepository.deleteAll()
                .thenMany(ingredientRepository.saveAll(
                        Flux.just(
                                new Ingredient("FLTO", "Flour Tortilla", Ingredient.Type.WRAP),
                                new Ingredient("FLTO2", "Flour Tortilla2", Ingredient.Type.WRAP),
                                new Ingredient("FLTO3", "Flour Tortilla3", Ingredient.Type.WRAP)
                        )
                ));
        StepVerifier.create(deleteAndInsert)
                .expectNextCount(3)
                .verifyComplete();
    }

    @Test
    public void shouldSaveAndFetchIngredient() {
        StepVerifier.create(ingredientRepository.findAll())
                .recordWith(ArrayList::new)
                .thenConsumeWhile(x -> true)
                .consumeRecordedWith(ingredients -> {
                    assertThat(ingredients).hasSize(3);
                    assertThat(ingredients).contains(
                            new Ingredient("FLTO", "Flour Tortilla", Ingredient.Type.WRAP)
                    );
                    assertThat(ingredients).contains(
                            new Ingredient("FLTO2", "Flour Tortilla2", Ingredient.Type.WRAP)
                    );
                    assertThat(ingredients).contains(
                            new Ingredient("FLTO3", "Flour Tortilla3", Ingredient.Type.WRAP)
                    );
                }).verifyComplete();

        StepVerifier.create(ingredientRepository.findBySlug("FLTO"))
                .assertNext(ingredient -> {
                    ingredient.equals(new Ingredient("FLTO", "Flour Tortilla", Ingredient.Type.WRAP));
                });
    }
}












