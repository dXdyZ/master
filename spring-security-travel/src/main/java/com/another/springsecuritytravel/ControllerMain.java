package com.another.springsecuritytravel;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ControllerMain {

    @GetMapping("/main")
    public String main() {
        return "main";
    }
}
