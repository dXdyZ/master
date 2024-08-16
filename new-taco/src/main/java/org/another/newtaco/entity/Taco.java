package org.another.newtaco.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.rest.core.annotation.RestResource;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Entity
@RestResource(rel = "tacos", path = "tacos")
public class Taco implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Date createAt = new Date();

    @NotNull
    @Size(min = 5, message = "Name must be at least 5 character long")
    private String name;

    @NotNull
    @Size(min = 1, message = "You must choose at least 1 ingredient")
    @ManyToMany
    private List<Ingredient> ingredients;

    public void addIngredient(Ingredient ingredient) {
        this.ingredients.add(ingredient);
    }
}
