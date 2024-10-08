package com.example.rsocket.controller;

import com.example.rsocket.entity.Alter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

@Slf4j
@Controller
public class AlterController {
    @MessageMapping("alert")
    public Mono<Void> setAlert(Mono<Alter> alertMono) {
        return alertMono
                .doOnNext(alert ->
                        log.info("{} alert ordered by {} at {}",
                                alert.getLevel(),
                                alert.getOrderedBy(),
                                alert.getOrderedAt())
                )
                .thenEmpty(Mono.empty());
    }
}
