package com.another.springsecuritytravel;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequestMapping("/product")
public class ProductController {

    @PostMapping("/add")
    public String add(@RequestParam String name) {
        log.info("Adding product: " + name);
        return "main";
    }
}
