package com.kot.backend.controller.pk;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BotInfoController {
    @RequestMapping("/")
    public String getBotInfo(){
        return "hhh";
    }
}
