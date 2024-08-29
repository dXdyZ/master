package com.example.reactivenewtaco.non_relationalBD;

import com.example.reactivenewtaco.non_relationalBD.mongoDB.entity.Ingredient;
import com.example.reactivenewtaco.non_relationalBD.mongoDB.entity.Taco;
import com.example.reactivenewtaco.non_relationalBD.mongoDB.entity.TacoOrder;
import com.example.reactivenewtaco.non_relationalBD.mongoDB.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import reactor.test.StepVerifier;

import java.util.UUID;

@DataMongoTest
public class OrderRepositoryTest {

    @Autowired
    OrderRepository orderRepository;

    @BeforeEach
    public void setup() {
        orderRepository.deleteAll().subscribe();
    }

    @Test
    public void shouldSavaAndFetchOrders() {
        TacoOrder order = createOrder();

        StepVerifier.create(orderRepository.save(order))
                .expectNext(order)
                .verifyComplete();

        StepVerifier.create(orderRepository.findById(order.getId()))
                .expectNext(order)
                .verifyComplete();

        StepVerifier.create(orderRepository.findAll())
                .expectNext(order)
                .verifyComplete();
    }

    private TacoOrder createOrder() {
        Taco taco = new Taco();
        taco.setId(UUID.randomUUID().toString());
        taco.setName("hello");
        taco.addIngredient(new Ingredient("FLTO", "Floure Tortilla", Ingredient.Type.VEGGIES));

        TacoOrder order = new TacoOrder();
        order.setId(UUID.randomUUID().toString());
        order.setUserId(UUID.randomUUID().toString());
        order.addTaco(taco);
        return order;
    }
}
