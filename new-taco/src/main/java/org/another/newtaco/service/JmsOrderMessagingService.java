package org.another.newtaco.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.another.newtaco.entity.TacoOrder;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class JmsOrderMessagingService implements OrderMessagingService {
    private final JmsTemplate jmsTemplate;

    @Override
    public void sendOrder(TacoOrder order) {
        jmsTemplate.convertAndSend(order, message -> {
            message.setStringProperty("_typeId", TacoOrder.class.getName());
            log.info("Setting _typeId property to: {}", TacoOrder.class.getName());
            return message;
        });
    }

}
