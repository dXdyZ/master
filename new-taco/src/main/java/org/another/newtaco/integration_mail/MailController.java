package org.another.newtaco.integration_mail;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/orderMail", produces = "application/json")
public class MailController {
    private static List<String> list = new ArrayList<>();

    @PostMapping
    public List<String> postOrder(@RequestBody String message) {
        log.info("My test order message: {}", message);
        list.add(message);
        return list;
    }
}
