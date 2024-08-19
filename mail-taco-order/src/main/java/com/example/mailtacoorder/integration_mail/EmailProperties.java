package com.example.mailtacoorder.integration_mail;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Slf4j
@Data
@ConfigurationProperties(prefix = "tacocloud.email")
@Component
public class EmailProperties {
    private String username;
    private String password;
    private String host;
    private String mailbox;
    private long pollRate = 30_000;

    public String getImapUrl() {
        String url =  String.format("imaps://%s:%s@%s/%s",
                this.username, this.password, this.host, this.mailbox);
        log.info("url: {}", url);
        return url;
    }
}
