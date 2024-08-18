package org.another.newtaco.integration_mail;

import lombok.RequiredArgsConstructor;
import org.springframework.integration.core.GenericHandler;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class OrderSubmitMessageHandler implements GenericHandler<EmailOrder> {

    private RestTemplate restTemplate;
    private ApiProperties apiProps;

    public OrderSubmitMessageHandler(RestTemplate restTemplate,
                                     ApiProperties apiProps) {
        this.restTemplate = restTemplate;
        this.apiProps = apiProps;
    }

    @Override
    public Object handle(EmailOrder payload, MessageHeaders headers) {
        restTemplate.postForObject(apiProps.getUlr(), order, String.class);
        return null;
    }

}
