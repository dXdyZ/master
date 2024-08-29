//package org.another.newtaco.security.reactive_security;
//
//import org.another.newtaco.repository.UserRepository;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
//import org.springframework.security.config.web.server.ServerHttpSecurity;
//import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
//import org.springframework.security.web.server.SecurityWebFilterChain;
//
//@Configuration
//@EnableWebFluxSecurity
//public class WebFluxSecurityConfig {
//
//    @Bean
//    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
//        return http
//                .authorizeExchange(exchange -> {
//                    exchange
//                            .pathMatchers("/api/tacos", "/orders").hasAuthority("USER")
//                            .anyExchange().permitAll();
//                }).build();
//    }
//
//    @Bean
//    public ReactiveUserDetailsService userDetailsService(UserRepository userRepository) {
//        return username -> {
//            return userRepository.findByUsername(username)
//                    .map(user -> {
//                        return user.toUserDetails();
//                    });
//        };
//    }
//}
