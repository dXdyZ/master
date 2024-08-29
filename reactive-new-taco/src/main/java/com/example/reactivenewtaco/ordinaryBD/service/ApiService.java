package com.example.reactivenewtaco.ordinaryBD.service;

import com.example.reactivenewtaco.ordinaryBD.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApiService {
    private final OrderRepository repository;

    public void testMessage() {
        repository.findAll()
                .doOnNext(order -> {
                    System.out.println("Deliver to: " + order.getDeliveryName());
                }).subscribe();
    }
}
