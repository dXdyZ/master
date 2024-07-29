package com.example.jwtserver.configs;

import com.example.jwtserver.utilit.JWTTokensUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.stream.Collectors;

/**
 * Через фильтр проходит запрос и мы можем с ним что то делать
 * Фильтр включается только кода пользователь стучиться
 * в защищенную область
 * Так же перекладывает данные из токена в контекст
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {
    private final JWTTokensUtil jwtTokensUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String username = null;
        String jwt = null;

        //Проверка токена
        if (authHeader != null && authHeader.startsWith("Bearer")) {
            //Вытаскиваем токен
            jwt = authHeader.substring(7);
            //Получаем пользователя
            try {
                username = jwtTokensUtil.getUsername(jwt);
            } catch (ExpiredJwtException e) { //Вышло время жизни токена
                log.debug("Время жизни токена вышла");
            } catch (SignatureException ex) { //Неверная подпись
                log.debug("Подпись неправильная");
            }
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    username,
                    null,
                    jwtTokensUtil.getRoles(jwt).stream().map(
                            SimpleGrantedAuthority::new
                    ).collect(Collectors.toList())
            );
            SecurityContextHolder.getContext().setAuthentication(token);
        }
        //Прогоняем цепочку фильтроф
        filterChain.doFilter(request, response);
    }
}
