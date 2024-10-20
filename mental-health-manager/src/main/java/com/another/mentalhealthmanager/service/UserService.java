package com.another.mentalhealthmanager.service;

import com.another.mentalhealthmanager.entity.DTO.JwtRequest;
import com.another.mentalhealthmanager.entity.user.Role;
import com.another.mentalhealthmanager.entity.user.Users;
import com.another.mentalhealthmanager.jwt.service.AuthService;
import com.another.mentalhealthmanager.repository.UserRepository;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Collections;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final AuthService authService;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public UserService(UserRepository userRepository, @Lazy AuthService authService) {
        this.userRepository = userRepository;
        this.authService = authService;
    }

    public ResponseEntity<?> register(Users users) {
        if (userRepository.findByUsername(users.getUsername()).isEmpty() && userRepository.findByEmail(users.getEmail()).isEmpty()) {
            users.setRole(Role.ROLE_USER);
            userRepository.save(users);
            return new ResponseEntity<>("Токен для авторизации запросов" + authService.createAuthToken(new JwtRequest(users.getUsername(), users.getPassword())), HttpStatus.CREATED);
        } else return new ResponseEntity<>("Имя пользователя или почта уже заняты", HttpStatus.BAD_REQUEST);
    }

    public Iterable<Users> findAllUsers() {
        return userRepository.findAll();
    }

    public ResponseEntity<?> findByUserName(String username) {
        Optional<Users> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            return ResponseEntity.ok(user);
        } else return ResponseEntity.notFound().build();
    }

    public Optional<Users> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public ResponseEntity<?> remember(Principal user) {
        Optional<Users> userGet = userRepository.findByUsername(user.getName());
        entityManager.clear();
        if (userGet.isPresent()) {
            return ResponseEntity.ok(userGet);
        } else return new ResponseEntity<>("Вы не авторизованны", HttpStatus.UNAUTHORIZED);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(
                String.format("Пользователь " + username + " не найден")
        ));
        return new User(
                users.getUsername(),
                users.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority(users.getRole().name()))
        );
    }
}
