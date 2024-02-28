package org.another.tacotools.controller;

import org.another.tacotools.model.Taco;
import org.another.tacotools.model.TacoOrder;
import org.another.tacotools.repository.OrderRepository;
import org.another.tacotools.service.JmsOrderMessagingService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/api/orders", produces = "application/json")
@CrossOrigin(origins = "http://localhost:8080")
public class OrderApiController {

    private OrderRepository repo;
    private JmsOrderMessagingService messagingService;

    public OrderApiController(OrderRepository repo, JmsOrderMessagingService messagingService) {
        this.repo = repo;
        this.messagingService = messagingService;
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public TacoOrder postOrder(@RequestBody TacoOrder order) {
        messagingService.sendOrder(order);
        return repo.save(order);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable("orderId") Long orderId) {
        try {
            repo.deleteById(orderId);
        } catch (EmptyResultDataAccessException e) {}
    }

    @GetMapping("/allOrder")
    public Iterable<TacoOrder> allOrder() {
        return repo.findAll();
    }
}
