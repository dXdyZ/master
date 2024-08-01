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
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
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
                .csrf(csrg -> csrg
                        .ignoringRequestMatchers("/api/**"))
                .oauth2ResourceServer((oauth2) -> oauth2.jwt(Customizer.withDefaults()))
                .authorizeHttpRequests((authorization) -> authorization
                        .requestMatchers("/design", "/orders/**").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/ingredients/**").hasAuthority("SCOPE_getIngredients")
                        .requestMatchers(HttpMethod.POST, "/api/ingredients/**").hasAuthority("SCOPE_writeIngredients")
                        .requestMatchers(HttpMethod.DELETE, "/api/ingredients/**").hasAuthority("SCOPE_deleteIngredients")
                        .requestMatchers(HttpMethod.GET, "/api/orders/**").hasAuthority("SCOPE_getOrders")
                        .requestMatchers(HttpMethod.POST, "/api/orders/**").hasAuthority("SCOPE_writeOrders")
                        .requestMatchers(HttpMethod.DELETE, "/api/order/**").hasAuthority("SCOPE_deleteOrders")
                        .requestMatchers("/", "/register").permitAll()
                        .requestMatchers("/css/**", "/js/**", "/image/**").permitAll()//включение стилей
                )
                    .httpBasic(Customizer.withDefaults())
                    .formLogin(Customizer.withDefaults())
                .build();

    }

}
