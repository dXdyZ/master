package org.another.tacotools.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@RequestMapping("/login")
@SessionAttributes("tacoOrder")
public class LoginController {
    @GetMapping
    public String getLogin() {
        return "login";
    }

    @PostMapping
    public String authorizationUsers() {
        return "redirect:/design";
    }
}
