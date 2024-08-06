package org.another.newtaco.component.receiver;

import jakarta.jms.JMSException;
import jakarta.jms.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.another.newtaco.entity.TacoOrder;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class JmsOrderReceiver implements OrderReceiver {
    private final JmsTemplate jms;
    private final MappingJackson2MessageConverter converter;

    @Override
    public TacoOrder receiveOrder() throws JMSException {
        Message message = jms.receive("tacocloud.order.queue");
        return (TacoOrder) converter.fromMessage(message);
    }
}
