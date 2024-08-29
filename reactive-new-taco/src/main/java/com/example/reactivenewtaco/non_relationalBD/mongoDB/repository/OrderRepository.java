package com.example.reactivenewtaco.non_relationalBD.mongoDB.repository;

import com.example.reactivenewtaco.non_relationalBD.mongoDB.entity.TacoOrder;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface OrderRepository extends ReactiveCrudRepository<TacoOrder, String> {
    Flux<TacoOrder> findByUserIdOrderByPlacedAtDesc(String userId, Pageable pageable);
}
