package com.doudou.freechat.Controllers;
 
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
 
@RestController
public class User {
 
    private static final String template = "Hello, ";
 
    @GetMapping("/getUser")
    public String greeting(@RequestParam(value = "name") String name) {
        return template + name;
    }
}
