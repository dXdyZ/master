//package org.another.newtaco.integration_mail;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.integration.core.GenericHandler;
//import org.springframework.messaging.MessageHeaders;
//import org.springframework.stereotype.Component;
//import org.springframework.web.client.RestTemplate;
//
//@Component
//@RequiredArgsConstructor
//public class OrderSubmitMessageHandler implements GenericHandler<EmailOrder> {
//
//    private final RestTemplate restTemplate;
//    private final ApiProperties apiProps;
//
//    @Override
//    public Object handle(EmailOrder order, MessageHeaders headers) {
//        restTemplate.postForObject(apiProps.getUrl(), order, String.class);
//        return null;
//    }
//
//}
