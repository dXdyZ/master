package com.example.reactivenewtaco.non_relationalBD.cassandra.repository;

import com.example.reactivenewtaco.non_relationalBD.cassandra.entity.Taco;
import com.example.reactivenewtaco.non_relationalBD.cassandra.entity.TacoOrder;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Repository
public interface OrderRepository extends ReactiveCrudRepository<TacoOrder, UUID> {
    Flux<Taco> findByUserOrderByPlacedAtDesc(User user, Pageable pageable);
}
