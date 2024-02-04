package org.another.springdata.proxy;

import org.another.springdata.model.Aircraft;
import org.another.springdata.repository.AircraftRepository;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@EnableScheduling
@Component
public class PlaneFinderPoller {
    private WebClient client = WebClient.create("http://localhost:7634/aitcraft");

    private final RedisConnectionFactory connectionFactory;

    private final AircraftRepository aircraftRepository;

    public PlaneFinderPoller(RedisConnectionFactory connectionFactory,
                             AircraftRepository aircraftRepository) {
        this.connectionFactory = connectionFactory;
        this.aircraftRepository = aircraftRepository;
    }

    @Scheduled(fixedRate = 1000)
    private void pollPlanes() {
        connectionFactory.getConnection().serverCommands().flushDb();

        client.get()
                .retrieve()
                .bodyToFlux(Aircraft.class)
                .filter(plane -> !plane.getReg().isEmpty())
                .toStream()
                .forEach(aircraftRepository::save);

        aircraftRepository.findAll().forEach(System.out::println);
    }
}
