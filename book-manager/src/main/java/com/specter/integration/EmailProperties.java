package com.specter.integration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "bookmanager.email")
public class EmailProperties {
    private String username;
    private String host;
    private String password;
    private Integer port;
}
