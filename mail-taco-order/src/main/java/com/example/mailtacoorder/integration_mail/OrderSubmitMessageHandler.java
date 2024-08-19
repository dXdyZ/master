package com.example.mailtacoorder.integration_mail;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.core.GenericHandler;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class OrderSubmitMessageHandler implements GenericHandler<EmailOrder> {

    private static final Logger log = LoggerFactory.getLogger(OrderSubmitMessageHandler.class);
    private final RestTemplate restTemplate;
    private final ApiProperties apiProps;

    @Override
    public Object handle(EmailOrder order, MessageHeaders headers) {
        log.info("url: {}", apiProps.getUrl());
        log.info("email order: {}", order.toString());
        restTemplate.postForObject(apiProps.getUrl(), order, String.class);
        return null;
    }

}
