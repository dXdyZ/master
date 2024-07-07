package org.another.firstdoc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class FirstDocApplication {

    public static void main(String[] args) {
        SpringApplication.run(FirstDocApplication.class, args);
    }

}
