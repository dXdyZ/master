package org.another.tacotools.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.another.tacotools.model.TacoOrder;
import org.another.tacotools.model.User;
import org.another.tacotools.repository.OrderRepository;
import org.another.tacotools.repository.UserRepository;
import org.another.tacotools.сonfigurationproperies.OrderProps;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import java.security.Principal;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("tacoOrder")
public class OrderController {
    private OrderRepository orderRepository;
    private UserRepository userRepository;
    private OrderProps orderProps;

    public OrderController(OrderRepository orderRepository,
                           UserRepository userRepository,
                           OrderProps orderProps) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.orderProps = orderProps;
    }

    @GetMapping("/current")
    public String orderForm() {
        return "orderForm";
    }

    @PostMapping
    public String processOrder(@Valid TacoOrder order,
                               Errors errors,
                               SessionStatus sessionStatus,
                               @AuthenticationPrincipal User user) {
        if (errors.hasErrors()) return "orderForm";

        order.setUser(user);

        orderRepository.save(order);
        sessionStatus.setComplete();


        return "redirect:/load";
    }

    @GetMapping
    public String ordersForUser(@AuthenticationPrincipal User user,
                                Model model) {
        Pageable pageable = PageRequest.of(0, orderProps.getPageSize());
        model.addAttribute("orders",
                orderRepository.findByUserOrderByPlacedAtDesc(user, pageable));

        return "orderList";
    }
}
