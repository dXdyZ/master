//package org.another.newtaco.component.receiver.jms;
//
//import jakarta.jms.JMSException;
//import lombok.RequiredArgsConstructor;
//import org.another.newtaco.entity.TacoOrder;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/receive-order")
//@RequiredArgsConstructor
//public class ReceiveController {
//    private final OrderReceiver orderReceiver;
//
//    @GetMapping
//    public TacoOrder getReceiveOrder() throws JMSException {
//        TacoOrder order = orderReceiver.receiveOrder();
//        return order;
//    }
//}
