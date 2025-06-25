package com.example.integrationteststudi.contoller;

import com.example.integrationteststudi.entity.User;
import com.example.integrationteststudi.repository.UserRepositoryImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserRepositoryImpl userRepository;

    public UserController(UserRepositoryImpl userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Integer id) {
        return userRepository.findById(id);
    }
}
