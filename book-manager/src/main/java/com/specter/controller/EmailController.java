package com.specter.controller;

import com.specter.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
@RequiredArgsConstructor
public class EmailController {
    private final EmailService emailService;

    @GetMapping("/orderCode/{email}")
    public void orderCode(@PathVariable("email") String emil) {
        emailService.sendEmail(emil, "Test", "1234");
    }
}
