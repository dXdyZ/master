package com.example.receiverrabbit.receive;

import com.example.receiverrabbit.entity.TacoOrderDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class Receiver{
    private final RabbitTemplate rabbit;

    public TacoOrderDTO receiveOrder() {
        return rabbit.receiveAndConvert("tacocloud.order.queue",
                new ParameterizedTypeReference<TacoOrderDTO>() {});
    }

    public List<TacoOrderDTO> receiverListOrder() {
        return rabbit.receiveAndConvert("tacocloud.order.queue",
                new ParameterizedTypeReference<List<TacoOrderDTO>>() {});
    }
}
