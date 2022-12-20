package com.doudou.freechat.controllers;
 
import com.doudou.freechat.common.api.CommonResult;
import com.doudou.freechat.common.api.IErrorCode;
import com.doudou.freechat.dao.UserDao;
import com.doudou.freechat.service.UserService;
import com.doudou.freechat.vo.UserVo;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

        UserVo userVo = userService.getUserInfoById(id);

        if (userVo.getId() == 0) {
            return CommonResult.failed("查询的用户不存在");
        }

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
        if (userName == null) {
            return CommonResult.failed("登录已过期");
        }
        UserVo userVo = userService.getUserInfoByName(userName);
        if (userVo.getId() == 0) {
            return CommonResult.failed("查询的用户不存在");
        }
        return CommonResult.success(userVo);
    }
}
