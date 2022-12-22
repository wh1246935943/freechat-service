package com.doudou.freechat.controllers;

import com.doudou.freechat.common.api.CommonResult;
import com.doudou.freechat.dao.UserDao;
import com.doudou.freechat.dto.UserRegisterParamDto;
import com.doudou.freechat.service.UserService;
import com.doudou.freechat.util.DDUtil;
import com.doudou.freechat.vo.LoginVo;
import com.doudou.freechat.vo.UserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 用户认证控制器
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Value("${jwt.expiration}")
    private int expiration;
    @Value("${jwt.token}")
    private String token;

    @Resource
    UserService userService;

    @Resource
    DDUtil ddUtil;

    /**
     * 用户登录，
     * 登录成功设置token
     * 并将用户名存入redis
     */
    @PostMapping("/login")
    public CommonResult login(
            @RequestBody Map<String, String> user,
            HttpServletResponse response
    ) {
        String userName = user.get("userName");
        String password = user.get("password");

        UserDao userDao = userService.getUserInfoByName(userName);
        if (userDao != null && password.equals(userDao.getPassword())) {
            String jwt = ddUtil.getToken(userDao.getUserName());

            Cookie cookie = new Cookie(token, jwt);
            cookie.setMaxAge(expiration);
            cookie.setPath("/");
            response.addCookie(cookie);

            LoginVo loginVo = new LoginVo();
            loginVo.setToken(jwt);
            loginVo.setId(userDao.getId());

            ddUtil.setRedisValue(userName, userDao.getId() + "");
            return CommonResult.success(loginVo, "登录成功");
        }
        return CommonResult.validateFailed("用户名或密码错误");
    }

    @PostMapping("/logout")
    public CommonResult logout(HttpServletRequest request) {
        String userName = ddUtil.getUserNameByToken(request);
        ddUtil.delRedisValue(userName);
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
