package com.another.mentalhealthmanager.controller;

import com.another.mentalhealthmanager.entity.DTO.JwtRequest;
import com.another.mentalhealthmanager.entity.user.Users;
import com.another.mentalhealthmanager.jwt.service.AuthService;
import com.another.mentalhealthmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final AuthService authService;

    @Autowired
    public UserController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }


    @GetMapping("/all")
    public Iterable<Users> getAllUsers() {
        return userService.findAllUsers();
    }

    @GetMapping("/remember")
    public ResponseEntity<?> getRemember(Principal user) {
        return userService.remember(user);
    }


    @PostMapping("/auth")
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) {
        return authService.createAuthToken(authRequest);
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> findByUsername(@PathVariable("username") String username) {
        return userService.findByUserName(username);
    }
}
