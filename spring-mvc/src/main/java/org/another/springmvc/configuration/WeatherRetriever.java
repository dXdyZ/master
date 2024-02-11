package org.another.springmvc.configuration;

import lombok.AllArgsConstructor;
import org.another.springmvc.model.Weather;
import org.another.springmvc.repository.WeatherRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.function.Consumer;

@AllArgsConstructor
@Configuration
public class WeatherRetriever {
    private final WeatherRepository repo;

    @Bean
    Consumer<List<Weather>>  retrieveWeatherPositions() {
        return acList -> {
          repo.deleteAll();
          repo.saveAll(acList);
          repo.findAll().forEach(System.out::println);
        };
    }
}
