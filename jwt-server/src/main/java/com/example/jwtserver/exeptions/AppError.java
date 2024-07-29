package com.example.jwtserver.exeptions;

import lombok.Data;

import java.util.Date;

/**
 * Исользуется для вывода ошибко
 */
@Data
public class AppError {
    private int status;
    private String message;
    private Date timestamp;

    public AppError(String message, int status) {
        this.message = message;
        this.status = status;
        this.timestamp = new Date();
    }
}
