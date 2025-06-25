package com.another.springsecuritytravel;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.DefaultCsrfToken;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class CustomCsrfTokenRepository implements CsrfTokenRepository {

    private final JpaTokenRepository jpaTokenRepository;

    @Autowired
    public CustomCsrfTokenRepository(JpaTokenRepository jpaTokenRepository) {
        this.jpaTokenRepository = jpaTokenRepository;
    }

    //Генерация токена
    @Override
    public CsrfToken generateToken(HttpServletRequest request) {
        String uuid = UUID.randomUUID().toString();
        return new DefaultCsrfToken("X-CSRF-TOKEN",
                "_csrf", uuid);
    }

    //Сохранение токена в бд на основе уникального идентификатора клиента
    @Override
    public void saveToken(CsrfToken token, HttpServletRequest request, HttpServletResponse response) {
        String identifier = request.getHeader("X-IDENTIFIER");
        Optional<Token> existingToken = jpaTokenRepository.findTokenByIdentifier(identifier); //1
        if (existingToken.isPresent()) { //2
            Token token1 = existingToken.get();
            token1.setToken(token.getToken());
        } else { //3
            Token token1 = new Token();
            token1.setToken(token.getToken());
            token1.setIdentifier(identifier);
            jpaTokenRepository.save(token1);
        }
    }

    @Override
    public CsrfToken loadToken(HttpServletRequest request) {
        String identifier = request.getHeader("X-IDENTIFIER");
        Optional<Token> existingToken = jpaTokenRepository
                .findTokenByIdentifier(identifier);
        if (existingToken.isPresent()) {
            Token token = existingToken.get();
            return new DefaultCsrfToken(
                    "X-CSRF-TOKEN",
                    "_csrf",
                    token.getToken()
            );
        }
        return null;
    }
}
