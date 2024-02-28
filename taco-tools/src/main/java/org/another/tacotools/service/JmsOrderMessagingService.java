package org.another.tacotools.service;


import org.another.tacotools.model.TacoOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class JmsOrderMessagingService{
    private JmsTemplate jms;


    @Autowired
    public JmsOrderMessagingService(JmsTemplate jms) {
        this.jms = jms;
    }

    public void sendOrder(TacoOrder order) {
        jms.convertAndSend("tacocloud.order.queue", order);
    }

}
