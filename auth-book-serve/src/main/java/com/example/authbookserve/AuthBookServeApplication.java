package com.example.authbookserve;

import com.example.authbookserve.entity.User;
import com.example.authbookserve.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@RequiredArgsConstructor
public class AuthBookServeApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthBookServeApplication.class, args);
    }

}
