package org.another.tacotools.service;

import org.another.tacotools.model.Taco;
import org.another.tacotools.model.TacoOrder;
import org.another.tacotools.model.User;
import org.another.tacotools.repository.OrderRepository;
import org.another.tacotools.repository.TacoRepository;
import org.another.tacotools.repository.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class OrderAdminService {
    private OrderRepository orderRepository;
    private UserRepository userRepository;
    private TacoRepository tacoRepository;

    public OrderAdminService(UserRepository userRepository,
                             OrderRepository orderRepository,
                             TacoRepository tacoRepository) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.tacoRepository = tacoRepository;
    }


    @PreAuthorize("hasRole('ADMIN')")
    public void deleteAllOrders() {
        orderRepository.deleteAll();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteAllUser() {
        userRepository.deleteAll();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteAllTaco() {
        tacoRepository.deleteAll();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public Iterable<User> findAllUsers() {
        Iterable<User> users = userRepository.findAll();
        return users;
    }

    @PreAuthorize("hasRole('ADMIN')")
    public Iterable<Taco> findAllTaco() {
        Iterable<Taco> tacos = tacoRepository.findAll();
        return tacos;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public void deleteUserById(Long id) {
       userRepository.deleteById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public void deleteTaco(Long id) {
        tacoRepository.deleteById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public void deleteTacoOrder(Long id) {
        orderRepository.deleteById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public Iterable<TacoOrder> findAllTacoOrder() {
        Iterable<TacoOrder> tacoOrders = orderRepository.findAll();
        return tacoOrders;
    }
}
