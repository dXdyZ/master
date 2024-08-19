package com.example.mailtacoorder.integration_mail;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "tacourl.api")
@Component
public class ApiProperties {
    private String url;
}
