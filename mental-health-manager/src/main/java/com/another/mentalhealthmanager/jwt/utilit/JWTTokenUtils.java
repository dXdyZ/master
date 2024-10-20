package com.another.mentalhealthmanager.jwt.utilit;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class JWTTokenUtils {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.lifetime}")
    private Duration jwtLifetime;

    //Из пользователя формируем токен
    public String generateToken(UserDetails userDetails) {
        //Формируем полезную нагрузку в токене
        Map<String, Object> claims = new HashMap<>();
        //Подшиваем в токен список ролей
        List<String> rolesList = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        claims.put("roles", rolesList);

        //Информация о времени создания и о сроке жизни

        //Время создания
        Date issuedDate = new Date();
        //Время жизни
        Date expiredDate = new Date(issuedDate.getTime() + jwtLifetime.toMillis());
        //Собираем токен
        return Jwts.builder()
                .setClaims(claims)
                //Тема токе
                .setSubject(userDetails.getUsername())
                .setIssuedAt(issuedDate)
                .setExpiration(expiredDate)
                //Подпись токена
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    //Из токена получаем имя пользователя
    public String getUsername(String token) {
        return getAllClaimsFromToken(token).getSubject();
    }

    //Получаем список ролей из токена
    public List<String> getRoles(String token) {
        return getAllClaimsFromToken(token).get("roles", List.class);
    }

    //Достаем данные
    //Claims - мапа данных, хранит данные, например имя пользователят такое то, роли такие то
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                //Расшифрофка с помощью секретного ключа
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }
}
