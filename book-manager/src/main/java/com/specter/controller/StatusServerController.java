package com.specter.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/status")
public class StatusServerController {

    @GetMapping
    public String getStatusServer() {
        return "UP";
    }
}
