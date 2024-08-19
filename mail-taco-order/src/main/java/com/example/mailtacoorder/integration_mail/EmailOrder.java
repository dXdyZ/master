package com.example.mailtacoorder.integration_mail;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class EmailOrder {
    private final String email;
    private List<TacoEmail> tacos = new ArrayList<>();

    public void addTaco(TacoEmail taco) {
        this.tacos.add(taco);
    }
}
