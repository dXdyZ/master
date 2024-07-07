package org.another.newtaco.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
public class Ingredient {
    private final String id;
    private final String name;
    private final Type type;

}

