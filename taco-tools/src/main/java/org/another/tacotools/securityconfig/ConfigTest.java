package org.another.tacotools.securityconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class ConfigTest {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/design", "/orders", "/orders/current", "/load").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/", "/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/design")
                )
                .logout((logoun) -> logoun.permitAll());

        return http.build();
    }

    @Bean
    public UserDetailsService userServiceInMemory(PasswordEncoder passwordEncoder) {
        List<UserDetails> userList = new ArrayList<>();
        userList.add(new User(
                "admin", passwordEncoder.encode("password"),
                Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"))
        ));
        userList.add(new User(
                "user", passwordEncoder.encode("12345"),
                Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"))
        ));

        return new InMemoryUserDetailsManager(userList);
    }
}
