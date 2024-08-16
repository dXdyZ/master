package org.another.newtaco.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.*;

import java.io.Serializable;


@Data
@Entity
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC, force = true)
public class Ingredient implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private final String id;
    private final String name;

    @Enumerated(EnumType.STRING)
    private final Type type;

}

