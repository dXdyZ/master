package org.another.newtaco.rest;

import lombok.extern.slf4j.Slf4j;
import org.another.newtaco.entity.Ingredient;
import org.hibernate.id.IncrementGenerator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class RestController {
    static RestTemplate rest = new RestTemplate();

    public static void main(String[] args) {
        System.out.println(getIngredientById("CARN"));
    }

    public static Ingredient getIngredientById(String ingredientId) {
        return rest.getForObject("http://localhost:8080/api/ingredients/{id}", Ingredient.class, ingredientId);
    }

    public static Ingredient getIngredientByIdMap(String ingredientId) {
        Map<String, String> urlValue = new HashMap<>();
        urlValue.put("id", ingredientId);
        return rest.getForObject("http://localhost:8080/api/ingredients/{id}", Ingredient.class, urlValue);
    }

    public static Ingredient getIngredientByIdURI(String ingredientId) {
        Map<String, String> urlValue = new HashMap<>();
        urlValue.put("id", ingredientId);
        URI url = UriComponentsBuilder
                .fromHttpUrl("http://localhost:8080/api/ingredients/{id}")
                .build(urlValue);
        return rest.getForObject(url, Ingredient.class);
    }

    public static Ingredient getIngredientByIdResponseEntity(String ingredientId) {
        ResponseEntity<Ingredient> responseEntity =
                rest.getForEntity("http://localhost:8080/api/ingredients/{id}", Ingredient.class, ingredientId);
        log.info("Fetched time: {}", responseEntity.getHeaders().getDate());
        return responseEntity.getBody();
    }

    public static void updateIngredient(Ingredient ingredient) {
        rest.put("http://127.0.0.1:8080/api/ingredients/{id}",
                ingredient, ingredient.getId());
    }

    public void deleteIngredient(Ingredient ingredient) {
        rest.delete("http://127.0.0.1:8080/api/ingredients/{id}",
                ingredient.getId());
    }

    public Ingredient creatIngredient(Ingredient ingredient) {
        ResponseEntity<Ingredient> responseEntity =
                rest.postForEntity("http://127.0.0.1:8080/api/ingredients",
                        ingredient, Ingredient.class);
        log.info("New resource created at {}", responseEntity.getHeaders().getLocation());
        return responseEntity.getBody();
    }
}
