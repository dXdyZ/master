package com.example.integrationteststudi;

import org.springframework.boot.SpringApplication;

public class TestIntegrationTestStudiApplication {

    public static void main(String[] args) {
        SpringApplication.from(IntegrationTestStudiApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
