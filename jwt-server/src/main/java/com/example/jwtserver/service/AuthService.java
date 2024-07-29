package com.example.jwtserver.service;

import com.example.jwtserver.dtos.JwtRequest;
import com.example.jwtserver.dtos.JwtResponse;
import com.example.jwtserver.dtos.RegistrUserDto;
import com.example.jwtserver.dtos.UserDto;
import com.example.jwtserver.enity.User;
import com.example.jwtserver.exeptions.AppError;
import com.example.jwtserver.utilit.JWTTokensUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final JWTTokensUtil jwtTokensUtil;
    private final AuthenticationManager authenticationManager;

    public ResponseEntity<?> crateNewUser(@RequestBody RegistrUserDto registrUserDto) {
        if (!registrUserDto.getPassword().equals(registrUserDto.getConfirmPassword())) {
            return new ResponseEntity<>(new AppError("Пароли не совпадают", HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
        }
        if (userService.findByUsername(registrUserDto.getUsername()).isPresent()) {
            return new ResponseEntity<>(new AppError("Пользователь с такими именем уже существует", HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
        }
        User user = userService.createNewUser(registrUserDto);
        return ResponseEntity.ok(new UserDto(user.getId(),
                user.getUsername(), user.getEmail()));
    }

    //Выдача токена
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
