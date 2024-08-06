package org.another.newtaco.component.receiver;

import jakarta.jms.JMSException;
import org.another.newtaco.entity.TacoOrder;

import java.util.List;

public interface OrderReceiver {
    TacoOrder receiveOrder() throws JMSException;
}
