package org.another.newtaco.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;


@Data
@Entity
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class Ingredient {
    @Id
    private final String id;
    private final String name;
    private final Type type;

}

