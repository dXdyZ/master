package org.another.newtaco;

import org.another.newtaco.entity.Ingredient;
import org.another.newtaco.entity.Type;
import org.another.newtaco.repository.IngredientRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class NewTacoApplication {

    public static void main(String[] args) {
        SpringApplication.run(NewTacoApplication.class, args);
    }


}
