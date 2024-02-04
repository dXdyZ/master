package org.another.springdataneo4j.proxy;


import org.another.springdataneo4j.model.Weather;
import org.another.springdataneo4j.repository.WeatherRepository;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@EnableScheduling
@Component
public class WeatherPoller {
    private WebClient client = WebClient.create("https://api.open-meteo.com/v1/forecast?latitude=54.7065&longitude=20.511&forecast_days=1");
    private final WeatherRepository repository;

    public WeatherPoller(WeatherRepository repository) {
        this.repository = repository;
    }

    @Scheduled(fixedRate = 1000)
    private void pollWeather() {
        repository.deleteAll();

        client.get()
                .retrieve()
                .bodyToFlux(Weather.class)
                .toStream()
                .forEach(repository::save);

        System.out.println("--- All weather ---");
        repository.findAll().forEach(System.out::println);
    }
}
