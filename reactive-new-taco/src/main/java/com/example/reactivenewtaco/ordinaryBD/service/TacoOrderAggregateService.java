package com.example.reactivenewtaco.ordinaryBD.service;

import com.example.reactivenewtaco.ordinaryBD.entity.Taco;
import com.example.reactivenewtaco.ordinaryBD.entity.TacoOrder;
import com.example.reactivenewtaco.ordinaryBD.repository.TacoRepository;
import com.example.reactivenewtaco.ordinaryBD.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TacoOrderAggregateService {
    private final TacoRepository tacoRepository;
    private final OrderRepository orderRepository;

    public Mono<TacoOrder> save(TacoOrder tacoOrder) {
        return Mono.just(tacoOrder)
                .flatMap(order -> {
                    List<Taco> tacos = order.getTacos();
                    order.setTacos(new ArrayList<>());
                    return tacoRepository.saveAll(tacos)
                            .map(taco -> {
                                order.addTaco(taco);
                                return order;
                            }).last();
                }).flatMap(orderRepository::save);
    }

    public Mono<TacoOrder> findById(Long id) {
        return orderRepository
                .findById(id)
                .flatMap(order -> {
                    return tacoRepository.findAllById(order.getTacoIds())
                            .map(taco -> {
                                order.addTaco(taco);
                                return order;
                            }).last();
                });
    }
}
















