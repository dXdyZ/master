package org.another.newtaco.controller.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.another.newtaco.entity.TacoOrder;
import org.another.newtaco.repository.OrderRepository;
import org.another.newtaco.service.OrderMessagingService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(path = "/ordersApi",
                produces = "application/json")
@CrossOrigin(origins = "http://localhost:8080")
@RequiredArgsConstructor
public class OrderApiController {
    private final OrderRepository repo;
    private final OrderMessagingService messagingService;


    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public TacoOrder postOrder(@RequestBody TacoOrder order) {
        messagingService.sendOrder(order);
        return repo.save(order);
    }

    @GetMapping
    public Iterable<TacoOrder> getOrder() {
        TacoOrder order = repo.findAll().iterator().next();
        log.info("taco order: {}", order.getId(), order.getTacos(), order.getUser());
        messagingService.sendOrder(order);
        return repo.findAll();
    }
}










