package com.example.kafkatest.service;

import com.example.kafkatest.dto.Product;
import com.example.kafkatest.dto.ProductDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Slf4j
@Service
public class ProductService {
    private KafkaTemplate<String, Product> template;

    public ProductService(KafkaTemplate<String, Product> template) {
        this.template = template;
    }

    public String createProduct(ProductDto productDto) {
        String uuid = UUID.randomUUID().toString();
        var product = new Product(uuid, productDto.getName(), productDto.getPrice(), productDto.getQuantity());
        try {
            SendResult<String, Product> result = template.send("test-topic", uuid, product).get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            log.error("Ошибка с отправкой сообщения ");
        }
        return uuid;
    }
}






