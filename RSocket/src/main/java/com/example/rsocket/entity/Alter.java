package com.example.rsocket.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class Alter {
    private Level level;
    private String orderedBy;
    private Instant orderedAt;


    public enum Level {
        YELLOW, ORANGE, RED, BLACK
    }
}
