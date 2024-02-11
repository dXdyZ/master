package org.another.spingdemoapi.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.another.spingdemoapi.model.Anime;
import org.another.spingdemoapi.repository.AnimeRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.Random;


@RequiredArgsConstructor
@Controller
public class AnimeController {
    @NonNull
    private final AnimeRepository repository;
    private WebClient client = WebClient.create("https://shikimori.one/api/animes/");

    @GetMapping("/anime")
    public String getAnime(Model model) {
        Random random = new Random();
        int randomNumber = random.nextInt(10000) + 1;

        WebClient updatedClient = client.mutate().baseUrl("https://shikimori.one/api/animes/" + randomNumber).build();

        updatedClient.get()
                .retrieve()
                .bodyToFlux(Anime.class)
                .toStream()
                .forEach(repository::save);

        model.addAttribute("currentAnime", repository.findAll());

        return "anime";
    }
}
