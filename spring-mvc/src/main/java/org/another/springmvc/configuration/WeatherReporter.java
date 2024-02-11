package org.another.springmvc.configuration;

import lombok.AllArgsConstructor;
import org.another.springmvc.model.Weather;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.List;
import java.util.function.Supplier;

@AllArgsConstructor
@Configuration
public class WeatherReporter {
    private final WeatherSerise wService;

    @Bean
    Supplier<Iterable<Weather>> weatherReposition() {
        return () -> {
            try {
                return wservice.getweather();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return List.of();
        };
    }
}
