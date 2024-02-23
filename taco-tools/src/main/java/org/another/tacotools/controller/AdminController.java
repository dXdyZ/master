package org.another.tacotools.controller;

import jakarta.validation.Valid;
import org.another.tacotools.model.Taco;
import org.another.tacotools.model.TacoOrder;
import org.another.tacotools.model.User;
import org.another.tacotools.repository.UserRepository;
import org.another.tacotools.service.OrderAdminService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.util.Optional;

@Controller
@RequestMapping("/admin")
@SessionAttributes("tacoOrder")
public class AdminController {
    private OrderAdminService adminService;
    private UserRepository userRepository;

    public AdminController(OrderAdminService adminService,
                           UserRepository userRepository) {
        this.adminService = adminService;
        this.userRepository = userRepository;
    }

    @GetMapping
    public String getAdmin() {
        return "admin";
    }

    @PostMapping("/deleteOrders")
    public String deleteAllOrders(SessionStatus sessionStatus) {
        adminService.deleteAllOrders();
        sessionStatus.isComplete();
        return "redirect:/admin";
    }

    @PostMapping("/deleteUsers")
    public String deleteAllUsers(SessionStatus sessionStatus) {
        adminService.deleteAllUser();
        sessionStatus.isComplete();
        return "redirect:/admin";
    }

    @PostMapping("/deleteAllTaco")
    public String deleteAllTaco(SessionStatus sessionStatus) {
        adminService.deleteAllTaco();
        sessionStatus.setComplete();
        return "redirect:/admin";
    }

    @ModelAttribute(name = "allUsers")
    public Iterable<User> findAllUsers(Model model) {
        return adminService.findAllUsers();
    }

    @ModelAttribute(name = "allTacos")
    public Iterable<Taco> findAllTacos(Model model) {
        return adminService.findAllTaco();
    }

    @ModelAttribute(name = "allTacoOrder")
    public Iterable<TacoOrder> findAllTacoOrder(Model model) {
        return adminService.findAllTacoOrder();
    }

    @PostMapping("/deleteUserById")
    public String deleteByUserID(@RequestParam("id") Long id) {
        adminService.deleteUserById(id);
        return "redirect:/admin";
    }

    @PostMapping("/deleteTacoById")
    public String deleteTacoById(@RequestParam("id") Long id) {
        adminService.deleteTaco(id);
        return "redirect:/admin";
    }

    @PostMapping("/deleteTacoOrderById")
    public String deleteTacoOrderById(@RequestParam("id") Long id) {
        adminService.deleteTacoOrder(id);
        return "redirect:/admin";
    }
}
