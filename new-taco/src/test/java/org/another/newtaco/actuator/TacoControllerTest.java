//package org.another.newtaco.reactor;
//
//import org.another.newtaco.entity.Ingredient;
//import org.another.newtaco.entity.Taco;
//import org.another.newtaco.entity.Type;
//import org.another.newtaco.funcrion_conteroller.RouterFunctionConfig;
//import org.another.newtaco.repository.TacoRepository;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.reactive.server.WebTestClient;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//import static org.mockito.ArgumentMatchers.any;
//import static reactor.core.publisher.Mono.when;
//
//public class TacoControllerTest {
//
//    @Test
//    public void shouldReturnRecentTacos() {
//        Taco[] tacos = {
//            testTaco(1L), testTaco(2L),
//            testTaco(3L), testTaco(4L),
//            testTaco(5L), testTaco(6L),
//            testTaco(7L), testTaco(8L),
//            testTaco(9L), testTaco(10L),
//            testTaco(11L), testTaco(12L),
//            testTaco(13L), testTaco(14L),
//            testTaco(15L), testTaco(16L)
//        };
//
//        Flux<Taco> tacoFlux = Flux.just(tacos);
//
//        TacoRepository tacoRepository = Mockito.mock(TacoRepository.class);
//        when(tacoRepository.findAll()).thenReturn(tacoFlux); //Фиктивный экземпляр TacoRepository
//
//        WebTestClient testClient = WebTestClient.bindToController(
//                         new RouterFunctionConfig(tacoRepository))
//                .build(); // Создать WebTestClient
//
//        testClient.get().uri("/reactor?recent")
//                .accept(MediaType.APPLICATION_JSON)
//                .exchange()
//                .expectStatus().isOk()
//                .expectBodyList(Taco.class)
//                .contains(Arrays.copyOf(tacos, 12));
//    }
//
//    private Taco testTaco(Long number) {
//        Taco taco = new Taco();
//        taco.setId(number);
//        taco.setName("Taco " + number);
//        List<Ingredient> ingredients = new ArrayList<>();
//        ingredients.add(
//                new Ingredient("INGA", "Ingredient A", Type.WRAP));
//        ingredients.add(
//                new Ingredient("INGB", "Ingredient B", Type.PROTEIN));
//        taco.setIngredients(ingredients);
//        return taco;
//    }
//
//    @SuppressWarnings("unchecked")
//    @Test
//    public void shouldSaveATaco() {
//        TacoRepository tacoRepository = Mockito.mock(TacoRepository.class); //Фикти экземпляр TacoRepository
//
//        WebTestClient testClient = WebTestClient.bindToController( //Создание WebTestClient
//                new RouterFunctionConfig(tacoRepository)
//                ).build();
//
//        Mono<Taco> unsavedTacoMono = Mono.just(testTaco(1L));
//        Taco savedTaco = testTaco(1L);
//        Flux<Taco> savedTacoMono = Flux.just(savedTaco);
//
//        when(tacoRepository.saveAll(any(Mono.class))).thenReturn(savedTacoMono); //Подготовить тестовые данные
//        testClient.post() //Послать запроса POST с новым рецептом
//                .uri("/reactor")
//                .contentType(MediaType.APPLICATION_JSON)
//                .body(unsavedTacoMono, Taco.class)
//                .exchange()
//                .expectStatus().isCreated() //Проверить ответ
//                .expectBody(Taco.class)
//                .isEqualTo(savedTaco);
//    }
//}
//
//
//
//
//
//
//
//
//
//
//
//
//
//
