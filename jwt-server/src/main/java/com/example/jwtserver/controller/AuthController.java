package com.example.jwtserver.controller;

import com.example.jwtserver.dtos.JwtRequest;
import com.example.jwtserver.dtos.JwtResponse;
import com.example.jwtserver.dtos.RegistrUserDto;
import com.example.jwtserver.enity.User;
import com.example.jwtserver.exeptions.AppError;
import com.example.jwtserver.service.UserService;
import com.example.jwtserver.utilit.JWTTokensUtil;
import jakarta.servlet.annotation.HttpConstraint;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final JWTTokensUtil jwtTokensUtil;
    private final AuthenticationManager authenticationManager;
    private final BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void crateNewUser(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.createNewUser(user);
    }



    //Выдача токена
    @PostMapping("/auth")
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) {
        log.info("Jwt request: {}", authRequest.getUsername(), authRequest.getPassword());

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

            log.info("user input: {}", authRequest.getUsername(),authRequest.getPassword());
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(new AppError("Неправильны логин или пароль", HttpStatus.UNAUTHORIZED.value()), HttpStatus.UNAUTHORIZED);
        }
        UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());
        log.info("user output: {}", userDetails.getAuthorities(), userDetails.getUsername(), userDetails.getPassword());

        String token = jwtTokensUtil.generateToken(userDetails);
        log.info("token output: {}", token);

        return ResponseEntity.ok(new JwtResponse(token));
    }

}
