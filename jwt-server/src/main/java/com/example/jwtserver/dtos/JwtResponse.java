package com.example.jwtserver.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Класс отдает токен
 */
@Data
@AllArgsConstructor
public class JwtResponse {
    private String token;
}
