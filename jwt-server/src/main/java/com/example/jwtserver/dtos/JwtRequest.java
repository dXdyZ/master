package com.example.jwtserver.dtos;

import lombok.Data;

/**
 * Класс для вопсприятия данных
 */
@Data
public class JwtRequest {
    private String username;
    private String password;
}
