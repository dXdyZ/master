package com.specter;

import com.specter.integration.EmailProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(EmailProperties.class)
public class SpecterApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpecterApplication.class, args);
    }
}
