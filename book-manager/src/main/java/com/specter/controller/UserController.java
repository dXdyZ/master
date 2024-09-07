package com.specter.controller;

import com.specter.entity.Users;
import com.specter.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/user", produces = "application/json")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/all")
    public Iterable<Users> getAllUser() {
        return userService.findAllUsers();
    }

    @PatchMapping("/update")
    public Object updateUserData(@RequestBody Users users, @AuthenticationPrincipal User user) {
        return userService.updateUserData(users, user);
    }

    @PostMapping("/register")
    public ResponseEntity<?> savedUser(@RequestBody Users users) {
        return userService.savedUser(users);
    }

    @PostMapping("/examinCode")
    public ResponseEntity<?> examinationCode(@RequestParam(name = "code") String code) {
        return userService.examinationCode(code);
    }

    @PostMapping("/setUserRole")
    public void setRole(@RequestParam(name = "username") String username,
                        @RequestParam(name = "role") String role) {
        userService.setUserRole(username, role);
    }

    @DeleteMapping("/deluser/{username}")
    public HttpStatus deleteUserByUsername(@PathVariable("username") String username) {
        return userService.deleteUserByUsername(username);
    }
}
