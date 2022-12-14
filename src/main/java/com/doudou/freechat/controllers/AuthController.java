package com.doudou.freechat.controllers;

import org.springframework.web.bind.annotation.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @PostMapping("/login")
    public Map<String, String> login(
            @RequestBody Map<String, String> user,
            HttpSession session,
            HttpServletResponse response
    ) {

        String userName = user.get("userName");
        String password = user.get("password");
        String code = user.get("code");

        Map<String, String> map = new HashMap<>();

        StringBuilder sb = new StringBuilder();
        sb.append(userName).append(" ").append(password).append(" ").append(code);
        String userId = sb.toString();

        map.put("userId", userId);

        return map;
    }

    @PostMapping("/verCode")
    public String getVerCode(@RequestBody Map<String, String> user) {

        return user.get("phoneNumber");
    }
}
