package org.another.tacotoolsmail.config;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Data
@ConfigurationProperties(prefix = "tacocloud.email")
@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmailProperties {
    String username;
    String password;
    String host;
    String mailbox;
    long pollRate = 30000;

    public String getImapUrl() {
        return String.format("imaps://%s:%s@%s/%s",
                this.username, this.password, this.host, this.mailbox);
    }
 }
