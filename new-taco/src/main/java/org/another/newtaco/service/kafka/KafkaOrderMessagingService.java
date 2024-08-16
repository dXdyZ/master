//package org.another.newtaco.service.kafka;
//
//import lombok.RequiredArgsConstructor;
//import org.another.newtaco.entity.TacoOrder;
//import org.another.newtaco.service.OrderMessagingService;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class KafkaOrderMessagingService implements OrderMessagingService {
//    private final KafkaTemplate<String, TacoOrder> kafkaTemplate;
//
//    @Override
//    public void sendOrder(TacoOrder order) {
//        kafkaTemplate.sendDefault(order);
//    }
//}