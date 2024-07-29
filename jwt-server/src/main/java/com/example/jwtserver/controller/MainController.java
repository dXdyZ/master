package com.example.jwtserver.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class MainController {
    //можно зайти без токена и авторизации
    @GetMapping("/unsecured")
    public String unsecuredData() {
        return "Unsecured Data";
    }

    //Защищенный
    @GetMapping("/secured")
    public String securedData() {
        return "Secured Data";
    }

    @GetMapping("/admin")
    public String adminData() {
        return "Admin Data";
    }

    @GetMapping("/info")
    public String userData(Principal principal) {
        return principal.getName();
    }


}










