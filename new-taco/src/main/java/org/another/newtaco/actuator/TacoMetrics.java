package org.another.newtaco.actuator;

import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import org.another.newtaco.entity.Ingredient;
import org.another.newtaco.entity.Taco;
import org.springframework.data.rest.core.event.AbstractRepositoryEventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TacoMetrics extends AbstractRepositoryEventListener<Taco> {
    private final MeterRegistry meterRegistry;

    @Override
    protected void onAfterCreate(Taco taco) {
        List<Ingredient> ingredients = taco.getIngredients();
        for (Ingredient ingredient : ingredients) {
            meterRegistry.counter("tacocloud",
                    "ingredient", ingredient.getId()).increment();
        }
    }
}
