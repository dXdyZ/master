package com.another.mentalhealthmanager;

import com.another.mentalhealthmanager.entity.user.Role;
import com.another.mentalhealthmanager.entity.user.Users;
import com.another.mentalhealthmanager.repository.UserRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class MentalHealthManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MentalHealthManagerApplication.class, args);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ApplicationRunner dataLoader(UserRepository userRepository) {
        return args -> {
            if (userRepository.findById(1L).isEmpty()) {
                userRepository.save(new Users().builder()
                        .username("admin")
                        .email("admin@admin.com")
                        .password(passwordEncoder().encode("admin"))
                        .role(Role.ROLE_ADMIN)
                        .build());
            };
        };
    }
}
