package com.another.springsecuritytravel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;

@Configuration
public class ProjectConfig {

    private final CustomCsrfTokenRepository customCsrfTokenRepository;

    @Autowired
    public ProjectConfig(CustomCsrfTokenRepository customCsrfTokenRepository) {
        this.customCsrfTokenRepository = customCsrfTokenRepository;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(c -> {
                    c.csrfTokenRepository(customCsrfTokenRepository);
                    c.csrfTokenRequestHandler( //1
                            new CsrfTokenRequestAttributeHandler()
                    );
                })
                .authorizeHttpRequests(c -> c.anyRequest().authenticated()
                )
                .build();
    }
}
