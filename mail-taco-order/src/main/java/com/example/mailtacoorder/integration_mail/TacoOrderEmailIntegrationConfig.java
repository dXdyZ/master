package com.example.mailtacoorder.integration_mail;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.mail.dsl.Mail;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Configuration
public class TacoOrderEmailIntegrationConfig {

    @Bean
    public IntegrationFlow tacoOrderEmailFlow(EmailProperties emailProperties,
                                              EmailToOrderTransformer emailOrderTransformer,
                                              OrderSubmitMessageHandler orderSubmitMessageHandler) {
        return IntegrationFlow
                .from(Mail.imapInboundAdapter(emailProperties.getImapUrl()),
                        e -> e.poller(Pollers.fixedDelay(emailProperties.getPollRate())))
                .transform(emailOrderTransformer)
                .handle(orderSubmitMessageHandler)
                .get();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
