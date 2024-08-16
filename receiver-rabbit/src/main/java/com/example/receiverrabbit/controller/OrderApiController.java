package com.example.receiverrabbit.controller;

import com.example.receiverrabbit.entity.TacoOrderDTO;
import com.example.receiverrabbit.receive.Receiver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/ordersData",
                produces = "application/json")
@CrossOrigin(origins = "http://localhost:8080")
@RequiredArgsConstructor
public class OrderApiController {
    private final Receiver receiver;

    @GetMapping
    public TacoOrderDTO getReceive() {
        return receiver.receiveOrder();
    }

    @GetMapping("/allOrder")
    public List<TacoOrderDTO> getOrders() {
        return receiver.receiverListOrder();
    }

}
