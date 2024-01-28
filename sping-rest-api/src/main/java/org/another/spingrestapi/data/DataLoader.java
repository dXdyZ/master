package org.another.spingrestapi.data;

import jakarta.annotation.PostConstruct;
import org.another.spingrestapi.model.Coffee;
import org.another.spingrestapi.repository.CoffeeRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataLoader {
    private final CoffeeRepository coffeeRepository;

    public DataLoader(CoffeeRepository coffeeRepository) {
        this.coffeeRepository = coffeeRepository;
    }

    @PostConstruct
    private void loadData(CoffeeRepository coffeeRepository) {
        coffeeRepository.saveAll(List.of(
                new Coffee("Cafe latter"),
                new Coffee("Cafe Kaputchino"),
                new Coffee("Cafe Americkano")
        ));
    }

}
