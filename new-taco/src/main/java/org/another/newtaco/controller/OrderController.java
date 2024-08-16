package org.another.newtaco.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.another.newtaco.entity.TacoOrder;
import org.another.newtaco.entity.User;
import org.another.newtaco.service.controller_service.OrderService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("tacoOrder")
public class OrderController {
    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/current")
    public String orderForm() {
        return "orderForm";
    }

    @GetMapping("/allOrders")
    public void getAllOrdersInMessageBroker() {
        orderService.getAllOrdersInMessage();
    }

    @PostMapping
    public String processOrder(@Valid TacoOrder order,
                               Errors errors,
                               SessionStatus sessionStatus,
                               @AuthenticationPrincipal User user) {
        if (errors.hasErrors()) return "orderForm";

        orderService.processOrder(order, user);

        sessionStatus.setComplete();
        return "redirect:/";
    }
}
