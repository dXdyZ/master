package com.example.reactivenewtaco.ordinaryBD.repository;

import com.example.reactivenewtaco.ordinaryBD.entity.Taco;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TacoRepository extends ReactiveCrudRepository<Taco, Long> {
}
