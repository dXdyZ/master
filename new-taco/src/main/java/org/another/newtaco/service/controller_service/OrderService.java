package org.another.newtaco.service.controller_service;

import lombok.extern.slf4j.Slf4j;
import org.another.newtaco.entity.TacoOrder;
import org.another.newtaco.entity.User;
import org.another.newtaco.entity.dto.TacoOrderDTO;
import org.another.newtaco.repository.OrderRepository;
import org.another.newtaco.service.OrderMessagingService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OrderService {
    private OrderRepository orderRepository;
    private OrderMessagingService messagingService;

    public OrderService(OrderRepository orderRepository,
                           OrderMessagingService messagingService) {
        this.orderRepository = orderRepository;
        this.messagingService = messagingService;
    }

    public void processOrder(TacoOrder order, User user) {
        order.setUser(user);

        orderRepository.save(order);

        messagingService.sendOrder(mapOrderInDTO(order));


        log.info("orderList size: {}", mapTacoOrderInOrderDTO().size());
    }

    public void getAllOrdersInMessage() {
        messagingService.sendOrderList(mapTacoOrderInOrderDTO());
    }

    private List<TacoOrderDTO> mapTacoOrderInOrderDTO() {
        Iterable<TacoOrder> orders = orderRepository.findAll();
        List<TacoOrder> order = new ArrayList<>();
        orders.forEach(order::add);

        return order.stream().map(this::mapOrderInDTO).collect(Collectors.toList());
    }


    private TacoOrderDTO mapOrderInDTO(TacoOrder order) {
        return new TacoOrderDTO(order.getId(), order.getPlaceAt(),
                new HashMap<>(){
                    {
                        put(order.getUser().getId(), new ArrayList<>() {{
                            add(order.getUser().getFullname());
                            add(order.getUser().getState());
                            add(order.getUser().getStreet());
                            add(order.getUser().getZip());
                            add(order.getUser().getPhoneNumber());
                        }});
                    }
                },
                order.getCcNumber(), order.getCcExpiration(), order.getCcCVV(), new HashMap<>(){{
            put(order.getTacos().iterator().next().getId(), new ArrayList<>() {{
                add(order.getTacos().iterator().next().getName());
                add(String.valueOf(order.getTacos().iterator().next().getCreateAt()));
                add(String.valueOf(order.getTacos().iterator().next().getIngredients()));
            }});
        }});
    }
}
