package org.another.springtest.proxy;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.another.springtest.model.Weather;
import org.another.springtest.repository.WeatherRepository;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@EnableScheduling
@Component
@RequiredArgsConstructor
public class WeatherPoller {
    @NonNull
    private WeatherRepository weatherRepository;
    private WebClient client = WebClient.create("https://api.open-meteo.com/v1/forecast?latitude=54.7065&longitude=20.511&forecast_days=1");

    @Scheduled(fixedRate = 240_000)
    private void pollWeather() {
        weatherRepository.deleteAll();

        client.get()
                .retrieve()
                .bodyToFlux(Weather.class)
                .toStream()
                .forEach(weatherRepository::save);

        weatherRepository.findAll().forEach(System.out::println);
    }
}
