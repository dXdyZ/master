package com.example.kafkatest.service;

import com.example.kafkatest.dto.ProductDto;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductServiceTestIT {

    @Autowired
    ProductService productService;


    @SneakyThrows
    @Test
    void createProduct() {
        var productDto = new ProductDto("test", 123.0, 1);

        String response = productService.createProduct(productDto);

        assertNotNull(response);
    }
}