package com.example.rsocket.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class GratuityOut {
    private BigDecimal billTotal;
    private int percent;
    private BigDecimal gratuity;
}
