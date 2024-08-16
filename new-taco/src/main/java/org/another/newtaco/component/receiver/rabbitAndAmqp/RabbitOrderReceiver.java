//package org.another.newtaco.component.receiver.rabbitAndAmqp;
//
//import lombok.RequiredArgsConstructor;
//import org.another.newtaco.entity.TacoOrder;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.core.ParameterizedTypeReference;
//import org.springframework.stereotype.Component;
//
//@Component
//@RequiredArgsConstructor
//public class RabbitOrderReceiver {
//    private final RabbitTemplate rabbit;
//
//    public TacoOrder receiveOrder() {
//        return rabbit.receiveAndConvert("tacocloud.order.queue",
//                new ParameterizedTypeReference<TacoOrder>() {});
//    }
//}
