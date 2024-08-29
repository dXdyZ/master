package com.example.rsocket.controller;

import com.example.rsocket.entity.GratuityIn;
import com.example.rsocket.entity.GratuityOut;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;

@Slf4j
@Controller
public class GratuityController {
    @MessageMapping("gratuity")
    public Flux<GratuityOut> calculate(Flux<GratuityIn> gratuityInFlux) {
        return gratuityInFlux
                .doOnNext(in -> log.info("Calculating gratuity: {}", in))
                .map(in -> {
                    double percentAsDecimal = in.getPercent() / 100.0;
                    BigDecimal gratuity = in.getBillTotal()
                            .multiply(BigDecimal.valueOf(percentAsDecimal));
                    return new GratuityOut(
                            in.getBillTotal(), in.getPercent(), gratuity
                    );
                });
    }
}
