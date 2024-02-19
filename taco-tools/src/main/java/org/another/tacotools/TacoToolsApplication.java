package org.another.tacotools;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "org.another.tacotools.model")
@EnableJpaRepositories(basePackages = "org.another.tacotools.repository")
public class TacoToolsApplication {

    public static void main(String[] args) {
        SpringApplication.run(TacoToolsApplication.class, args);
    }

}
