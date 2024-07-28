package org.another.newtaco.security;

import org.another.newtaco.entity.User;
import org.another.newtaco.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return username -> {
            User user = userRepository.findByUsername(username);
            if (user != null) return user;
            throw new UsernameNotFoundException("User '" + username + "' not found");
        };
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests((authorization) -> authorization
                        .requestMatchers("/design", "/orders/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/API/ingredients").hasRole("USER")
                        .requestMatchers(HttpMethod.DELETE, "/API/ingredients/**").hasRole("USER")
                        .requestMatchers("/", "/register").permitAll()
                        .requestMatchers(HttpMethod.GET, "/API/ingredients").hasRole("USER")
                        .requestMatchers("/api/**").anonymous()
                        .requestMatchers("/css/**", "/js/**", "/image/**").permitAll()//включение стилей
                )
                    .httpBasic(Customizer.withDefaults())
                    .formLogin(Customizer.withDefaults())
                .build();

    }

}
