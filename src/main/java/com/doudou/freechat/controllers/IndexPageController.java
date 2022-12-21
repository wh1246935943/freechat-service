package com.doudou.freechat.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexPageController {
    @GetMapping("/")
    public String init() {
        return "欢迎造访";
    }
}
