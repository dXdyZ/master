package com.example.receiverrabbit.receive;

import com.example.receiverrabbit.entity.TacoOrderDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class Receiver{
    private final RabbitTemplate rabbit;

    public TacoOrderDTO receiveOrder() {
        return rabbit.receiveAndConvert("tacocloud.order.queue",
                new ParameterizedTypeReference<TacoOrderDTO>() {});
    }

    public List<TacoOrderDTO> receiveOrderList() {
        Message message = rabbit.receive("tacocloud.order.queue");
        if (message != null) {
            // Проверяем заголовок
            String headerValue = (String) message.getMessageProperties().getHeaders().get("ALL");
            if ("ORDER".equals(headerValue)) {
                // Конвертируем сообщение в объект
                @SuppressWarnings("unchecked")
                List<TacoOrderDTO> orderList = (List<TacoOrderDTO>) rabbit.getMessageConverter().fromMessage(message);
                return orderList;
            }
        }
        return Collections.emptyList(); // Возврат пустого списка, если сообщение не соответствует критериям
    }
}
