package com.another.mentalhealthmanager.repository;

import com.another.mentalhealthmanager.entity.MentalState;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MentalStateRepository extends CrudRepository<MentalState, Long> {
}
