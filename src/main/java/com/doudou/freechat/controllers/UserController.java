package com.doudou.freechat.controllers;
 
import com.doudou.freechat.common.api.CommonResult;
import com.doudou.freechat.dao.UserDao;
import com.doudou.freechat.service.UserService;
import com.doudou.freechat.vo.UserVo;
import io.jsonwebtoken.Jwts;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
public class UserController {

    @Value("${jwt.token}")
    private String token;
    @Value("${jwt.secret}")
    private String secret;

    @Resource
    private UserService userService;

    @GetMapping("/{id}")
    public CommonResult getUserInfo(@PathVariable long id) {
        UserDao userDao = userService.getUserInfoById(id);
        if (userDao == null) {
            return CommonResult.failed("查询的用户不存在");
        }
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(userDao, userVo);
        return CommonResult.success(userVo);
    }

    @GetMapping("/info")
    public CommonResult getMyUserInfo(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String userName = null;
        try {
            if (cookies != null) {
                for(Cookie c :cookies ){
                    if (c.getName().equals(token)) {
                        userName = Jwts.parser()
                                .setSigningKey(secret)
                                .parseClaimsJws(c.getValue().replace("Bearer", ""))
                                .getBody()
                                .getSubject();
                    }
                }
            }
        } catch (Exception e) {}
        UserDao userDao = userService.getUserInfoByName(userName);
        if (userDao == null) {
            return CommonResult.validateFailed();
        }
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(userDao, userVo);
        return CommonResult.success(userVo);
    }
}
