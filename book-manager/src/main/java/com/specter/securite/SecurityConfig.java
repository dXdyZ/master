package com.specter.securite;

import com.specter.entity.Role;
import com.specter.entity.Users;
import com.specter.repository.UserRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Date;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/user/register", "/user/examinCode", "/status").anonymous()
                        .requestMatchers("/book/**", "/file/**", "/email/**").authenticated()
                        .requestMatchers("/user/all", "/user/deluser/**", "user/setUserRole").hasRole("ADMIN")
                )
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean
    public ApplicationRunner dataLoader(UserRepository userRepository) {
        return args -> {
            if (userRepository.findById(1L).isEmpty()) {
                userRepository.save(new Users().builder()
                        .username("admin")
                        .createAt(new Date())
                        .email("admin@admin.com")
                        .password(passwordEncoder().encode("admin"))
                        .role(Role.ROLE_ADMIN).build());
            }
        };
    }
}







