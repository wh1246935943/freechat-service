package com.doudou.freechat.controllers;

import com.doudou.freechat.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/login")
    public String login(
            @RequestBody Map<String, String> user,
            HttpServletResponse response
    ) {
        String userName = user.get("userName");
        String password = user.get("password");

        if (userName != null && password != null) {
            String token = authService.login(userName, password);
            if (token == null) {
                return "账号不存在或密码错误";
            }
            Cookie cookie = new Cookie("token", token);
            cookie.setMaxAge(60 * 60 * 24); // 设置过期时间为一小时
            cookie.setPath("/"); // 设置路径
            response.addCookie(cookie);
            return token;
        }

        return "账号不存在或密码错误";
    }

    @PostMapping("/verCode")
    public String getVerCode(@RequestBody Map<String, String> user) {

        return user.get("phoneNumber");
    }
}
