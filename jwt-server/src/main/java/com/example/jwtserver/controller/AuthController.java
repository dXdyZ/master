package com.example.jwtserver.controller;

import com.example.jwtserver.dtos.JwtRequest;
import com.example.jwtserver.dtos.RegistrUserDto;
import com.example.jwtserver.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> crateNewUser(@RequestBody RegistrUserDto registrUserDto) {
        return authService.crateNewUser(registrUserDto);
    }


    //Выдача токена
    @PostMapping("/auth")
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) {
        return authService.createAuthToken(authRequest);
    }

}
