package com.example.jwtserver.service;

import com.example.jwtserver.dtos.RegistrUserDto;
import com.example.jwtserver.enity.User;
import com.example.jwtserver.repositories.RoleRepo;
import com.example.jwtserver.repositories.UserRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor //создает конструктор с полями
public class UserService implements UserDetailsService {
    private final UserRepo userRepo;
    private final RoleService roleService;
    private final BCryptPasswordEncoder encoder;

    public Optional<User> findByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    //Пеобразование пользователя в spring user
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(
                String.format("Пользователься '%s' не найден", username)
        ));
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getRoles().stream().map(role ->
                                new SimpleGrantedAuthority(role.getName()))
                        .collect(Collectors.toList())
        );
    }

    public User createNewUser(RegistrUserDto registrUserDto) {
        User user = new User();
        user.setEmail(registrUserDto.getEmail());
        user.setUsername(registrUserDto.getUsername());
        user.setPassword(encoder.encode(registrUserDto.getPassword()));
        user.setRoles(List.of(roleService.getUserRole()));
        return userRepo.save(user);
    }
}












