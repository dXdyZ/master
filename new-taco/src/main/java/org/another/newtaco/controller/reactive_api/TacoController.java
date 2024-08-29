//package org.another.newtaco.controller.reactive_api;
//
//import lombok.RequiredArgsConstructor;
//import org.another.newtaco.entity.Taco;
//import org.another.newtaco.repository.TacoRepository;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.*;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//
//@RestController
//@RequestMapping(path = "/reactor")
//@RequiredArgsConstructor
//public class TacoController {
//    private final TacoRepository repository;
//
//    @GetMapping
//    public Flux<Taco> recentTacos() {
//        return repository.findAll().take(12);
//    }
//
//    @GetMapping("/{id}")
//    public Mono<Taco> tacoById(@PathVariable("id") Long id) {
//        return repository.findById(id);
//    }
//
//    @PostMapping(consumes = "application/json")
//    @ResponseStatus(HttpStatus.CREATED)
//    public Mono<Taco> postTaco(@RequestBody Mono<Taco> tacoMono) {
//        return tacoMono.flatMap(repository::save);
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
