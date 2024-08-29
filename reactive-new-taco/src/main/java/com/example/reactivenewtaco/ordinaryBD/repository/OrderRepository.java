package com.example.reactivenewtaco.ordinaryBD.repository;

import com.example.reactivenewtaco.ordinaryBD.entity.TacoOrder;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends ReactiveCrudRepository<TacoOrder, Long> {
}
