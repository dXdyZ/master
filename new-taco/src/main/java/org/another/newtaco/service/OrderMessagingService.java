package org.another.newtaco.service;

import org.another.newtaco.entity.TacoOrder;

public interface OrderMessagingService {
    void sendOrder(TacoOrder order);
}
