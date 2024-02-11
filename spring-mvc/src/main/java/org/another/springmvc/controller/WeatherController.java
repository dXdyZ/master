package org.another.springmvc.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.another.springmvc.model.Weather;
import org.another.springmvc.repository.WeatherRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RequiredArgsConstructor
@Controller
public class WeatherController {
    @NonNull
    private final WeatherRepository repository;

    private WebClient client = WebClient.create("https://api.open-meteo.com/v1/forecast?latitude=54.7065&longitude=20.511&forecast_days=1");

    @GetMapping("/weather")
    public String getCurrentWeather(Model model) {
        repository.deleteAll();

        client.get()
                .retrieve()
                .bodyToFlux(Weather.class)
                .toStream()
                .forEach(repository::save);

        model.addAttribute("currentWeather", repository.findAll());

        return "weather";
    }
}
