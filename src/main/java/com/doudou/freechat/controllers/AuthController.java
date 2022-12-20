package com.doudou.freechat.controllers;

import com.doudou.freechat.common.api.CommonResult;
import com.doudou.freechat.dto.UserRegisterParamDto;
import com.doudou.freechat.service.AuthService;
import com.doudou.freechat.vo.LoginVo;
import com.doudou.freechat.vo.UserVo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Resource
    AuthService authService;
    @Value("${jwt.token}")
    private String token;
    @Value("${jwt.expiration}")
    private int expiration;

    @PostMapping("/login")
    public CommonResult login(
            @RequestBody Map<String, String> user,
            HttpServletResponse response
    ) {
        LoginVo loginVo = authService.login(user.get("userName"), user.get("password"));
        String loginVoToken = loginVo.getToken();
        if (loginVoToken == null) {
            return CommonResult.validateFailed("用户名或密码错误");
        }
        Cookie cookie = new Cookie(token, loginVoToken);
        cookie.setMaxAge(expiration);
        cookie.setPath("/");
        response.addCookie(cookie);
        return CommonResult.success(loginVo, "登录成功");
    }

    @PostMapping("/logout")
    public CommonResult logout() {
        return CommonResult.success(null, "退出成功");
    }

    @PostMapping("/verCode")
    public CommonResult getVerCode(@RequestBody Map<String, String> user) {
        return CommonResult.success("123456");
    }

    @PostMapping("/register")
    public CommonResult register(@RequestBody UserRegisterParamDto userRegisterParam) {
        UserVo userVo = authService.register(userRegisterParam);
        if (userVo == null) {
            return CommonResult.failed("用户名已存在");
        }
        return CommonResult.success(userVo, "账号注册成功");
    }
}
