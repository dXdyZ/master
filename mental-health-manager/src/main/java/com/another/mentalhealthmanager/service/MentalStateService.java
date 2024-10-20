package com.another.mentalhealthmanager.service;

import com.another.mentalhealthmanager.repository.MentalStateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MentalStateService {
    private final MentalStateRepository mentalStateRepository;

    @Autowired
    public MentalStateService(MentalStateRepository mentalStateRepository) {
        this.mentalStateRepository = mentalStateRepository;
    }
}
