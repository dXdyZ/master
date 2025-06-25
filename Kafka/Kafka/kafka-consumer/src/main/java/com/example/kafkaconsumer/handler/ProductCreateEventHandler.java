package com.example.kafkaconsumer.handler;

import com.example.kafkaconsumer.dto.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@KafkaListener(topics = "test-topic")
public class ProductCreateEventHandler {

    @KafkaHandler
    public void handel(Product product) {
        log.info("Product: {}", product);
    }
}






