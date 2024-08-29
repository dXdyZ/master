package org.another.newtaco.reactive_rest_api;

import lombok.RequiredArgsConstructor;
import org.another.newtaco.entity.Ingredient;
import org.another.newtaco.entity.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Component
public class RestApi {
    @Autowired
    WebClient webClient;

    public Mono<Ingredient> getIngredientById(String ingredientId) {
        Mono<Ingredient> ingredientMono = webClient
                .get()
                .uri("/api/ingredients/{id}", ingredientId)
                .exchangeToMono(cr -> {
                    if (cr.headers().header("X_UNAVAILABLE").contains("true")) return Mono.empty();
                    return Mono.just(cr);
                })
                .flatMap(cr -> cr.bodyToMono(Ingredient.class));
        ingredientMono.subscribe();

        return ingredientMono;
    }

}

@Configuration
class RestAPIConfig {
    @Bean
    public WebClient webClient() {
        return WebClient.create("http://localhost:8080");
    }
}













