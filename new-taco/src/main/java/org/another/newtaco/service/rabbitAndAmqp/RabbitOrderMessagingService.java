package org.another.newtaco.service.rabbitAndAmqp;

import lombok.RequiredArgsConstructor;
import org.another.newtaco.entity.dto.TacoOrderDTO;
import org.another.newtaco.service.OrderMessagingService;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RabbitOrderMessagingService implements OrderMessagingService {
    private final RabbitTemplate rabbit;

    @Override
    public void sendOrder(TacoOrderDTO order) {
        rabbit.convertAndSend("tacocloud.order.queue", order);
    }

    @Override
    public void sendOrderList(List<TacoOrderDTO> orderDTOS) {
        rabbit.convertAndSend("tacocloud.order.queue", orderDTOS,
                new MessagePostProcessor() {
                    @Override
                    public Message postProcessMessage(Message message) throws AmqpException {
                        MessageProperties props = message.getMessageProperties();
                        props.setHeader("ALL", "ORDER");
                        return message;
                    }
                });
    }
}









