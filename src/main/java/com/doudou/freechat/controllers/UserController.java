package com.doudou.freechat.controllers;
 
import com.doudou.freechat.dao.UserDao;
import com.doudou.freechat.service.UserService;
import com.doudou.freechat.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;
 
    @PostMapping("/login")
    public Map<String, String> login(
            @RequestBody Map<String, String> user,
            HttpSession session,
            HttpServletResponse response
    ) {

        String userName = user.get("userName");
        String password = user.get("password");
        String code = user.get("code");

//        session.setAttribute("sessionId", "123");

        Map<String, String> map = new HashMap<>();

        StringBuilder sb = new StringBuilder();
        sb.append(userName).append(" ").append(password).append(" ").append(code);
        String userId = sb.toString();

        map.put("userId", userId);

        Cookie cookie = new Cookie("sessionId", "123");
        cookie.setMaxAge(60 * 60 * 24); // Set the cookie to expire in 24 hours.
        response.addCookie(cookie);

        return map;
    };

    @GetMapping("/{id}")
    public UserVo getUserInfo(@PathVariable Long id) {

        UserDao userDao = userService.getUserInfo(id);

        UserVo userVo = new UserVo();

        userVo.setUserName(userDao.getUserName());
        userVo.setPersonalitySign(userDao.getPersonalitySign());
        userVo.setId(userDao.getId());

        return userVo;
    }
}
