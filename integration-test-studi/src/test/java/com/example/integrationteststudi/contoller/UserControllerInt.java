package com.example.integrationteststudi.contoller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerInt {

    @Autowired
    private TestRestTemplate template;

    @Test
    void shouldReturnHelloMessage() {
        ResponseEntity<String> response = template.getForEntity("/users/hello", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

    }
}







