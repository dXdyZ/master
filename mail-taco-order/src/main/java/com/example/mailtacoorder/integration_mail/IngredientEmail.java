package com.example.mailtacoorder.integration_mail;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IngredientEmail {
    private String code;
    private String name;
}
