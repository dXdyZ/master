package org.another.tacotoolsmail.config;


import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
public class Taco {
    private Long id;

    private Date createdAt = new Date();

    private String name;

    private List<Ingredient> ingredients;

    public void addIngredient(Ingredient ingredient) {
        this.ingredients.add(ingredient);
    }
}
