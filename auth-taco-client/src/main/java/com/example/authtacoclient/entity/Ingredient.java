package com.example.authtacoclient.entity;


import lombok.*;


@Data
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC, force = true)
public class Ingredient {
    private final String id;
    private final String name;
    private final Type type;

}

