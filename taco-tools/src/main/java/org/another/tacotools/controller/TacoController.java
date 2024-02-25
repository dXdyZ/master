//package org.another.tacotools.controller;
//
//import jakarta.persistence.EntityNotFoundException;
//import org.another.tacotools.model.Taco;
//import org.another.tacotools.model.TacoOrder;
//import org.another.tacotools.repository.OrderRepository;
//import org.another.tacotools.repository.TacoRepository;
//import org.springframework.dao.EmptyResultDataAccessException;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Sort;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Optional;
//
//@RestController
//@RequestMapping(path = "/api/tacos", // Обработка запроса с указанным путем
//        produces = "application/json") // Сообщаем что клиент может обрабатывать только ответы в json
//@CrossOrigin(origins = "https://tacocloud:8080") // Разришаем обработку межсайтовых запросов
//public class TacoController {
//    private TacoRepository tacoRepository;
//    private OrderRepository orderRepository;
//
//    public TacoController(TacoRepository tacoRepository,
//                          OrderRepository orderRepository) {
//        this.tacoRepository = tacoRepository;
//        this.orderRepository = orderRepository;
//    }
//
//    @GetMapping(params = "recent")
//    public Iterable<Taco> recentTaco() {
//        PageRequest page = PageRequest.of(0, 12, Sort.by("createdAt").descending());
//
//        return tacoRepository.findAllTaco(page);
//    }
//
//    @GetMapping("/{id}")
//    public Optional<Taco> tacoById(@PathVariable("id") Long id) {
//        return tacoRepository.findById(id);
//    }
//
//    @PostMapping(consumes = "application/json")// Определяем формат входных данных
//    @ResponseStatus(HttpStatus.CREATED)
//    public Taco postTaco(@RequestBody Taco taco) { //Говорит что тело запроса должно быть преобразованно в указанный объект
//        return tacoRepository.save(taco);
//    }
//
//    @PatchMapping(path = "/{orderId}", consumes = "application/json")
//    public TacoOrder putOrder(@PathVariable("orderId") Long orderId,
//                              @RequestBody TacoOrder patch) {
//        TacoOrder order = orderRepository.findById(orderId)
//                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
//        if (patch.getDeliveryName() != null) {
//            order.setDeliveryName(patch.getDeliveryName());
//        }
//        if (patch.getDeliveryStreet() != null) {
//            order.setDeliveryStreet(patch.getDeliveryStreet());
//        }
//        if (patch.getDeliveryCity() != null) {
//            order.setDeliveryCity(patch.getDeliveryCity());
//        }
//        if (patch.getDeliveryState() != null) {
//            order.setDeliveryState(patch.getDeliveryState());
//        }
//        if (patch.getDeliveryZip() != null) {
//            order.setDeliveryZip(patch.getDeliveryZip());
//        }
//        if (patch.getCcNumber() != null) {
//            order.setCcNumber(patch.getCcNumber());
//        }
//        if (patch.getCcExpiration() != null) {
//            order.setCcExpiration(patch.getCcExpiration());
//        }
//        if (patch.getCcCVV() != null) {
//            order.setCcCVV(patch.getCcCVV());
//        }
//        return orderRepository.save(order);
//    }
//
//    @DeleteMapping("/{orderId}")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void deleteOrder(@PathVariable("orderId") Long orderId) {
//        try {
//            orderRepository.deleteById(orderId);
//        } catch (EmptyResultDataAccessException e) {}
//    }
//}
