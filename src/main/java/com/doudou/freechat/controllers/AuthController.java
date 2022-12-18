package com.doudou.freechat.controllers;

import com.doudou.freechat.service.AuthService;
import com.doudou.freechat.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthService authService;
    @Value("${jwt.token}")
    private String token;
    @Value("${jwt.expiration}")
    private int expiration;

    @PostMapping("/login")
    public LoginVo login(
            @RequestBody Map<String, String> user,
            HttpServletResponse response
    ) {
        String userName = user.get("userName");
        String password = user.get("password");

        LoginVo loginVo = null;

        if (userName != null && password != null) {
            loginVo = authService.login(userName, password);
            if (loginVo != null) {
                Cookie cookie = new Cookie(this.token, loginVo.getToken());
                cookie.setMaxAge(this.expiration);
                cookie.setPath("/"); // 设置路径
                response.addCookie(cookie);
            }
        }

        return loginVo;
    }

    @PostMapping("/verCode")
    public String getVerCode(@RequestBody Map<String, String> user) {

        return user.get("phoneNumber");
    }
}
