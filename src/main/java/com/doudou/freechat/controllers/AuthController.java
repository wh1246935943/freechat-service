package com.doudou.freechat.controllers;

import com.doudou.freechat.common.api.CommonResult;
import com.doudou.freechat.dao.UserDao;
import com.doudou.freechat.dto.UserRegisterParamDto;
import com.doudou.freechat.service.UserService;
import com.doudou.freechat.util.DDUtil;
import com.doudou.freechat.vo.LoginVo;
import com.doudou.freechat.vo.UserVo;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Value("${jwt.expiration}")
    private int expiration;
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.token}")
    private String token;

    @Resource
    UserService userService;

    @Resource
    DDUtil ddUtil;

    @PostMapping("/login")
    public CommonResult login(
            @RequestBody Map<String, String> user,
            HttpServletResponse response
    ) {
        String userName = user.get("userName");
        String password = user.get("password");

        ddUtil.setRedisValue("userName", userName);

        UserDao userDao = userService.getUserInfoByName(userName);
        if (userDao != null && password.equals(userDao.getPassword())) {
            String jwt = Jwts.builder()
                    .setSubject(userDao.getUserName())
                    .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000L))
                    .signWith(SignatureAlgorithm.HS512, secret)
                    .compact();
            /**
             * 设置token
             */
            Cookie cookie = new Cookie(token, jwt);
            cookie.setMaxAge(expiration);
            cookie.setPath("/");
            response.addCookie(cookie);
            /**
             * 返回登录成功后的用户id
             * 和用户token
             */
            LoginVo loginVo = new LoginVo();
            loginVo.setToken(jwt);
            loginVo.setId(userDao.getId());
            return CommonResult.success(loginVo, "登录成功");
        }
        return CommonResult.validateFailed("用户名或密码错误");
    }

    @PostMapping("/logout")
    public CommonResult logout() {

        String userName = ddUtil.getRedisValue("userName");

        System.out.printf(userName);

        return CommonResult.success(null, "退出成功");
    }

    @PostMapping("/verCode")
    public CommonResult getVerCode(@RequestBody Map<String, String> user) {
        return CommonResult.success("123456");
    }

    @PostMapping("/register")
    public CommonResult register(@RequestBody UserRegisterParamDto userRegisterParam) {
        UserDao userDao = userService.addUser(userRegisterParam);
        if (userDao == null) {
            return CommonResult.failed("用户名已存在");
        }
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(userDao, userVo);
        return CommonResult.success(userVo, "账号注册成功");
    }
}
