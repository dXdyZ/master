package org.another.newtaco.service;

import org.another.newtaco.entity.TacoOrder;
import org.another.newtaco.entity.dto.TacoOrderDTO;

import java.util.List;

public interface OrderMessagingService {
    void sendOrder(TacoOrderDTO order);
    void sendOrderList(List<TacoOrderDTO> orderDTOS);
}
