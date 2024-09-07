//package org.another.newtaco.funcrion_conteroller;
//
//import static org.springframework.web.reactive.function.server.RequestPredicates.*;
//import static org.springframework.web.reactive.function.server.RouterFunctions.route;
//import static org.springframework.web.reactive.function.server.ServerResponse.ok;
//import static reactor.core.publisher.Mono.just;
//
//import lombok.RequiredArgsConstructor;
//import org.another.newtaco.entity.Taco;
//import org.another.newtaco.repository.TacoRepository;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.reactive.function.server.RouterFunction;
//import org.springframework.web.reactive.function.server.ServerRequest;
//import org.springframework.web.reactive.function.server.ServerResponse;
//import reactor.core.publisher.Mono;
//
//import java.net.URI;
//
//@Configuration
//@RequiredArgsConstructor
//public class RouterFunctionConfig {
//    private final TacoRepository repository;
//
//    @Bean
//    public RouterFunction<?> routerFunction() {
//        return route(GET("/reactor")
//                .and(queryParam("recent", t -> t != null)),
//                this::recents)
//                .andRoute(POST("/reactor"), this::postTaco);
//    }
//
//    public Mono<ServerResponse> recents(ServerRequest request) {
//        return ServerResponse.ok()
//                .body(repository.findAll().take(2), Taco.class);
//    }
//
//    public Mono<ServerResponse> postTaco(ServerRequest request) {
//        return request.bodyToMono(Taco.class)
//                .flatMap(taco -> repository.save(taco))
//                .flatMap(savedTaco -> {
//                    return ServerResponse
//                            .created(URI.create("http://localhost:8080/reactor/" +
//                                    savedTaco.getId()))
//                            .body(savedTaco, Taco.class);
//                });
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
//
//
//
//
