package com.example.jwtserver.service;

import com.example.jwtserver.enity.Role;
import com.example.jwtserver.enity.User;
import com.example.jwtserver.repositories.RoleRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepo repo;

    public Role getUserRole() {
        return repo.findByName("ROLE_USER").get();
    }
}
