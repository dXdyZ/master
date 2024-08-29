package com.example.reactivenewtaco.ordinaryBD.controller;

import com.example.reactivenewtaco.ordinaryBD.service.ApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ApiController {
    private final ApiService apiService;

    public void test() {
        apiService.testMessage();
    }
}
